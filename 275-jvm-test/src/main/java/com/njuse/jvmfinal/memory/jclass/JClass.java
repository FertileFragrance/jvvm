package com.njuse.jvmfinal.memory.jclass;

import com.njuse.jvmfinal.classloader.ClassLoader;
import com.njuse.jvmfinal.classloader.classfileparser.ClassFile;
import com.njuse.jvmfinal.classloader.classfileparser.FieldInfo;
import com.njuse.jvmfinal.classloader.classfileparser.MethodInfo;
import com.njuse.jvmfinal.classloader.classfileparser.constantpool.ConstantPool;
import com.njuse.jvmfinal.classloader.classfilereader.classpath.EntryType;
import com.njuse.jvmfinal.datastruct.array.*;
import com.njuse.jvmfinal.datastruct.NonArrayObject;
import com.njuse.jvmfinal.datastruct.array.ArrayType;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.methodArea.StaticVars;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;
import com.njuse.jvmfinal.memory.threadStack.ThreadStack;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JClass {                                   // 一个JClass对象就是一个解析好的ClassFile对象
    private short accessFlags;                          // 访问标志，直接获取
    private String name;                                // 类名，直接获取
    private String superClassName;                      // 父类的类名，直接获取
    private String[] interfaceNames;                    // 所有接口的名字，直接获取
    private RuntimeConstantPool runtimeConstantPool;    // 运行时常量池，先获取常量池再解析获得
    private Field[] fields;                             // 所有字段，先获取字段表再解析获得
    private Method[] methods;                           // 所有方法，先获取方法表再解析获得
    private EntryType loadEntryType;                    // 最终加载此类的加载器
    private JClass superClass;                          // 父类，类加载时获取
    private JClass[] interfaces;                        // 所有接口（也是JClass类型），类加载时获取
    private int instanceSlotCount;                      // 实例变量占用槽的数量，类加载时获取
    private int staticSlotCount;                        // 静态变量占用槽的数量，类加载时获取
    private StaticVars staticVars;                      // 静态变量，类加载时获取
    private InitState initState;                        // 类加载的状态

    /*
        构造方法，参数为一个ClassFile对象，完成了：
            1. 对访问标志、类名、父类的类名、接口名的获取
            2. 对字段表和方法表的解析，生成Field和Method对象
            3. 将常量池的内容加载进运行时常量池中
     */
    public JClass(ClassFile classFile) {
        this.accessFlags = classFile.getAccessFlags();
        this.name = classFile.getClassName();
        if (!this.name.equals("java/lang/Object")) {
            // index of super class of java/lang/Object is 0
            this.superClassName = classFile.getSuperClassName();
        } else {
            this.superClassName = "";
        }
        this.interfaceNames = classFile.getInterfaceNames();
        this.fields = parseFields(classFile.getFields());
        this.methods = parseMethods(classFile.getMethods());
        this.runtimeConstantPool = parseRuntimeConstantPool(classFile.getConstantPool());
    }

    /**
     * 无参构造方法用于数组类的加载
     */
    public JClass() {}

    /*
        字段解析，将FieldInfo转换为Field
     */
    private Field[] parseFields(FieldInfo[] info) {
        int len = info.length;
        fields = new Field[len];
        for (int i = 0; i < len; i++) {
            fields[i] = new Field(info[i], this);
        }
        return fields;
    }

    /*
        方法解析，将MethodInfo转换为Method
     */
    private Method[] parseMethods(MethodInfo[] info) {
        int len = info.length;
        methods = new Method[len];
        for (int i = 0; i < len; i++) {
            methods[i] = new Method(info[i], this);
        }
        return methods;
    }

    /*
        将ClassFile中的常量池存入运行时常量池中
     */
    private RuntimeConstantPool parseRuntimeConstantPool(ConstantPool cp) {
        return new RuntimeConstantPool(cp, this);
    }

    /**
     * 根据类的访问标志判断该类是否被某个修饰符修饰
     * @return 是或否
     */
    public boolean isPublic() {
        return (this.accessFlags & AccessFlags.ACC_PUBLIC) != 0;
    }

    public boolean isFinal() {
        return (this.accessFlags & AccessFlags.ACC_FINAL) != 0;
    }

    public boolean isSuper() {
        return (this.accessFlags & AccessFlags.ACC_SUPER) != 0;
    }

    public boolean isInterface() {
        return (this.accessFlags & AccessFlags.ACC_INTERFACE) != 0;
    }

    public boolean isAbstract() {
        return (this.accessFlags & AccessFlags.ACC_ABSTRACT) != 0;
    }

    public boolean isSynthetic() {
        return (this.accessFlags & AccessFlags.ACC_SYNTHETIC) != 0;
    }

    public boolean isAnnotation() {
        return (this.accessFlags & AccessFlags.ACC_ANNOTATION) != 0;
    }

    /**
     * 获得该类所在的包名
     * @return 包名
     */
    public String getPackageName() {
        int index = name.lastIndexOf('/');
        if (index > 0) {
            return name.substring(0, index);
        } else {
            return "";
        }
    }

    /**
     * 判断caller是否有对本callee的权限
     * @param caller 呼叫者，调用方
     * @return 是或否
     */
    public boolean isAccessibleTo(JClass caller) {
        if (this.isPublic()) {
            return true;
        }
        if (!this.isPublic() && caller.getPackageName().equals(this.getPackageName())) {
            return true;
        }
        return false;
    }

    /**
     * 得到JClass对象的主方法（如果有的话）
     * @return 主方法或空
     */
    public Method getMainMethod() {
        for (Method m : methods) {
            if (m.getName().equals("main") && m.getDescriptor().equals("([Ljava/lang/String;)V") && m.isStatic()) {
                return m;
            }
        }
        return null;
    }

    /**
     * 类初始化，即执行类构造器<clinit>()方法
     * @param threadStack 当前线程的线程栈
     * @param jClass 要进行初始化的类
     */
    public void initClass(ThreadStack threadStack, JClass jClass) {
        jClass.setInitState(InitState.BUSY);
        for(Method m : jClass.getMethods()) {
            if(m.getName().equals("<clinit>") && m.getDescriptor().equals("()V") && m.isStatic()) {
                threadStack.pushStackFrame(new StackFrame(threadStack, m, m.getMaxLocal(), m.getMaxStack()));
                break;
            }
        }
        if(jClass.getSuperClass() != null && !jClass.isInterface() &&
                jClass.getSuperClass().getInitState() == InitState.PREPARED) {
            initClass(threadStack, jClass.getSuperClass());
        }
        jClass.setInitState(InitState.SUCCESS);
    }

    /**
     * 创建一个非数组的对象
     * @return 创建好的对象
     */
    public NonArrayObject newObject() {
        return new NonArrayObject(this);
    }

    /**
     * 创建一个数组对象
     * @param length 数组长度
     * @return 创建好的对象
     */
    public ArrayObject newArrayObject(int length) {
        switch (this.getName().charAt(1)) {
            case 'Z':
                return new BooleanArrayObject(this, length, ArrayType.T_BOOLEAN);
            case 'C':
                return new CharArrayObject(this, length, ArrayType.T_CHAR);
            case 'F':
                return new FloatArrayObject(this, length, ArrayType.T_FLOAT);
            case 'D':
                return new DoubleArrayObject(this, length, ArrayType.T_DOUBLE);
            case 'B':
                return new ByteArrayObject(this, length, ArrayType.T_BYTE);
            case 'S':
                return new ShortArrayObject(this, length, ArrayType.T_SHORT);
            case 'I':
                return new IntArrayObject(this, length, ArrayType.T_INT);
            case 'J':
                return new LongArrayObject(this, length, ArrayType.T_LONG);
            default:
                return new RefArrayObject(this, length, ArrayType.T_REF);
        }
    }

    /**
     * 由组件类的得到要创建的数组类
     * @return 加载完成的数组类
     */
    public JClass getArrayClass() {
        String arrayClassName;
        if (this.getName().charAt(0) == '[') {
            arrayClassName = this.name;
        } else if (this.getPrimitiveTypeComponentClassName() != null) {
            arrayClassName = this.getPrimitiveTypeComponentClassName();
        } else {
            arrayClassName = "L" + this.getName() + ";";
        }
        arrayClassName = "[" + arrayClassName;
        return ClassLoader.getInstance().loadClass(arrayClassName, null, this.getLoadEntryType());
    }

    /**
     * 判断组件类是否是基本数据类型，若是则直接返回类名
     * @return 类名或空
     */
    private String getPrimitiveTypeComponentClassName() {
        if (this.getName().equals("Z") || this.getName().equals("C") || this.getName().equals("F") ||
                this.getName().equals("D") || this.getName().equals("B") || this.getName().equals("S") ||
                this.getName().equals("I") || this.getName().equals("J")) {
            return this.name;
        }
        return null;
    }

    /**
     * 通过类加载获得组件类，以此递归创建多维数组
     * @return 加载完成的组件类
     */
    public JClass getComponentClass() {
        String componentClassName = this.name.substring(1);
        if (componentClassName.charAt(0) == '[') {
            return ClassLoader.getInstance().loadClass(componentClassName, null, this.getLoadEntryType());
        } else if (componentClassName.charAt(0) == 'L') {
            componentClassName = componentClassName.substring(0, componentClassName.length() - 1);
            return ClassLoader.getInstance().loadClass(componentClassName, null, this.getLoadEntryType());
        } else {
            return ClassLoader.getInstance().loadClass(this.getName(), null, this.getLoadEntryType());
        }
    }

    /**
     * 判断此类是不是数组类
     * @return 是或否
     */
    public boolean isArray() {
        return this.name.charAt(0) == '[';
    }

    /**
     * 判断此类是不是另一个类的子类
     * @param jClass 另一个类
     * @return 是或否
     */
    public boolean isSubClassOf(JClass jClass) {
        JClass clazz = this.getSuperClass();
        while (clazz != null) {
            if (clazz == jClass) {
                return true;
            }
            clazz = clazz.getSuperClass();
        }
        return false;
    }

    /**
     * 判断此类是不是实现了某个接口
     * @param jClass 接口
     * @return 是或否
     */
    public boolean isImplementOf(JClass jClass) {
        JClass[] interfaces = this.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (JClass clazz : interfaces) {
                if (clazz == jClass) {
                    return true;
                }
            }
            for (int i = 0; i < interfaces.length; i++) {
                if (interfaces[i].isImplementOf(jClass)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 得到数组类的原始类型
     * @return 数组类的原始类型
     */
    public String getPrimitiveType() {
        int index;
        for (index = 0; index < this.getName().length(); index++) {
            if (this.getName().charAt(index) != '[') {
                break;
            }
        }
        return this.getName().substring(index);
    }

}
