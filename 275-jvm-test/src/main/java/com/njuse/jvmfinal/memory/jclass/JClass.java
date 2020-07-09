package com.njuse.jvmfinal.memory.jclass;

import com.njuse.jvmfinal.classloader.classfileparser.ClassFile;
import com.njuse.jvmfinal.classloader.classfileparser.FieldInfo;
import com.njuse.jvmfinal.classloader.classfileparser.MethodInfo;
import com.njuse.jvmfinal.classloader.classfileparser.constantpool.ConstantPool;
import com.njuse.jvmfinal.classloader.classfilereader.classpath.EntryType;
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

}
