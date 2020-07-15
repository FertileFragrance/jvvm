package com.njuse.jvmfinal.classloader;

import com.njuse.jvmfinal.classloader.classfileparser.ClassFile;
import com.njuse.jvmfinal.classloader.classfilereader.ClassFileReader;
import com.njuse.jvmfinal.classloader.classfilereader.classpath.EntryType;
import com.njuse.jvmfinal.datastruct.NullObject;
import com.njuse.jvmfinal.memory.jclass.AccessFlags;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.*;
import com.njuse.jvmfinal.memory.methodArea.MethodArea;
import com.njuse.jvmfinal.memory.methodArea.StaticVars;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 类加载器，用于类加载
 */
public class ClassLoader {

    /**
     * 采用懒汉式单例模式
     */
    private static ClassLoader classLoader = new ClassLoader();

    private ClassLoader() {}

    public static ClassLoader getInstance() {
        return classLoader;
    }

    private MethodArea methodArea = MethodArea.getInstance();   /**持有方法区实例的引用*/

    /**
     * 类加载的入口，判断加载的是不是数组类并继续完成
     * @param className 要加载的类名
     * @param classPath 类路径，准确说是用户类路径
     * @param initiatingEntry 最初开始加载的类路径的类型
     * @return 加载好的JClass对象
     */
    public JClass loadClass(String className, String classPath, EntryType initiatingEntry) {
        if (ClassFileReader.getBootClassPath() == null) {
            ClassFileReader.setBootClassPath(className, null);
        }
        if (ClassFileReader.getExtClassPath() == null) {
            ClassFileReader.setExtClassPath(className, null);
        }
        if (ClassFileReader.getUserClassPath() == null) {
            ClassFileReader.setUserClassPath(className, classPath);
        }
        if (this.methodArea.findClass(className) != null) {
            return this.methodArea.findClass(className);
        } else if (className.charAt(0) == '[') {
            return this.loadArrayClass(className,classPath, initiatingEntry);
        } else {
            return this.loadNonArrayClass(className, classPath, initiatingEntry);
        }
    }

    /**
     * 非数组类加载的入口，执行结束就是加载完成了
     * @param className 要加载的类名
     * @param classPath 类路径，准确说是用户类路径
     * @param initiatingEntry 最初开始加载的类路径的类型
     * @return 加载好的JClass对象
     */
    private JClass loadNonArrayClass(String className,String classPath, EntryType initiatingEntry) {
        Pair<byte[], Integer> res = ClassFileReader.readClassFile(className, initiatingEntry);
        byte[] data = res.getLeft();
        EntryType definingEntry = new EntryType(res.getRight());
        JClass ret = this.defineClass(data, classPath, definingEntry);
        this.linkClass(ret);
        return ret;
    }

    /**
     * 数组类加载的入口，执行结束就是加载完成了
     * @param className 要加载的类名
     * @param classPath 类路径，准确说是用户类路径
     * @param initiatingEntry 最初开始加载的类路径的类型
     * @return 加载好的JClass对象
     */
    private JClass loadArrayClass(String className,String classPath, EntryType initiatingEntry) {
        JClass ret = new JClass();
        ret.setAccessFlags((short) AccessFlags.ACC_PUBLIC);
        ret.setName(className);
        ret.setSuperClassName("java/lang/Object");
        ret.setSuperClass(classLoader.loadClass(ret.getSuperClassName(), null, initiatingEntry));
        ret.setLoadEntryType(initiatingEntry);
        methodArea.addClass(ret.getName(), ret);
        ret.setInitState(InitState.SUCCESS);
        return ret;
    }

    /**
     * 一个神奇的不知如何确切翻译的方法，主要完成了四件事：
     * 1. 检查是否已经规定了加载器，实际上不需要，直接将byte[]转换为JClass即可
     * 2. 递归加载父类
     * 3. 加载类的接口
     * 4. 设置类的loadEntryType，并将JClass加到方法区
     * @param data class文件的字节表示
     * @param classPath 类路径，准确说是用户类路径
     * @param definingEntry 最终加载该类的类加载器类型
     * @return JClass对象
     */
    private JClass defineClass(byte[] data, String classPath, EntryType definingEntry) {
        ClassFile classFile = new ClassFile(data);
        JClass jClass = new JClass(classFile);
        jClass.setLoadEntryType(definingEntry);
        this.resolveSuperClass(jClass, classPath);
        this.resolveInterfaces(jClass, classPath);
        methodArea.addClass(jClass.getName(), jClass);
        return jClass;
    }

    /**
     * 此方法用于递归加载父类并且在jClass对象中设置它的superClass，直到加载的类是java/lang/Object
     * @param jClass 要加载父类的子类，若是Object类则直接返回
     * @param classPath 父类路径，为了简单和子类相同，实际上可能不同
     */
    private void resolveSuperClass(JClass jClass, String classPath) {
        if (jClass.getName().equals("java/lang/Object")) {
            return;
        } else {
            jClass.setSuperClass(this.loadClass(jClass.getSuperClassName(), classPath, jClass.getLoadEntryType()));
        }
    }

    /**
     * 此方法用于加载类的接口并在jClass对象中设置它的interfaces，接口也是一种类
     * @param jClass 要加载接口的类
     * @param classPath 接口路径，为了简单和类相同，实际上可能不同
     */
    private void resolveInterfaces(JClass jClass, String classPath) {
        JClass[] interfaces = new JClass[jClass.getInterfaceNames().length];
        for (int i = 0; i < jClass.getInterfaceNames().length; i++) {
            interfaces[i] = this.loadClass(jClass.getInterfaceNames()[i], classPath, jClass.getLoadEntryType());
        }
        jClass.setInterfaces(interfaces);
    }

    /**
     * 此方法是类加载连接阶段的入口
     * @param jClass 要进行连接的类
     */
    private void linkClass(JClass jClass) {
        this.verify(jClass);
        this.prepare(jClass);
        methodArea.addStaticVars(jClass);
    }

    private void verify(JClass jClass) {
    }

    /**
     * 连接过程的准备阶段，此方法是入口
     * @param jClass 要进行准备阶段的类
     */
    private void prepare(JClass jClass) {
        this.calStaticFieldSlotIDs(jClass);
        this.allocAndInitStaticVars(jClass);
        this.calInstanceFieldSlotIDs(jClass);
        jClass.setInitState(InitState.PREPARED);
    }

    /**
     * 此方法用于计算进行类加载的这个类中静态变量所需要的槽的个数
     * 设置了jClass的staticSlotCount和静态变量在槽中的索引
     * @param jClass 进行类加载的类
     */
    private void calStaticFieldSlotIDs(JClass jClass) {
        Field[] fields = jClass.getFields();
        int slotID = 0;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isStatic()) {
                fields[i].setSlotID(slotID);
                slotID++;
                if (fields[i].isDoubleOrLong()) {
                    slotID++;
                }
            }
        }
        jClass.setStaticSlotCount(slotID);
    }

    /**
     * 给静态变量分配内存并赋值，分两种情况
     * @param jClass 进行准备阶段的类
     */
    private void allocAndInitStaticVars(JClass jClass) {
        jClass.setStaticVars(new StaticVars(jClass.getStaticSlotCount()));
        Field[] fields = jClass.getFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isStatic() && fields[i].isFinal()) {
                this.loadValueFromRuntimeConstantPool(jClass, fields[i]);
            } else if (fields[i].isStatic()) {
                this.loadDefaultValue(jClass, fields[i]);
            }
        }
    }

    /**
     * 给带有final修饰符的静态字段从运行时常量池中赋值
     * @param jClass 进行准备阶段的类
     * @param field 该类的其中一个静态字段
     */
    private void loadValueFromRuntimeConstantPool(JClass jClass, Field field) {
        RuntimeConstantPool runtimeConstantPool = jClass.getRuntimeConstantPool();
        StaticVars staticVars = jClass.getStaticVars();
        int constValueIndex = field.getConstValueIndex();
        switch (field.descriptor.charAt(0)) {
            case 'B':
                int byteVal = ((IntWrapper) runtimeConstantPool.getConstant(constValueIndex)).getValue();
                staticVars.setInt(field.getSlotID(), byteVal);
                break;
            case 'C':
                int charVal = ((IntWrapper) runtimeConstantPool.getConstant(constValueIndex)).getValue();
                staticVars.setInt(field.getSlotID(), charVal);
                break;
            case 'D':
                double doubleVal = ((DoubleWrapper) runtimeConstantPool.getConstant(constValueIndex)).getValue();
                staticVars.setDouble(field.getSlotID(), doubleVal);
                break;
            case 'F':
                float floatVal = ((FloatWrapper) runtimeConstantPool.getConstant(constValueIndex)).getValue();
                staticVars.setFloat(field.getSlotID(), floatVal);
                break;
            case 'I':
                int intVal = ((IntWrapper) runtimeConstantPool.getConstant(constValueIndex)).getValue();
                staticVars.setInt(field.getSlotID(), intVal);
                break;
            case 'J':
                long longVal = ((LongWrapper) runtimeConstantPool.getConstant(constValueIndex)).getValue();
                staticVars.setLong(field.getSlotID(), longVal);
                break;
            case 'S':
                int shortVal = ((IntWrapper) runtimeConstantPool.getConstant(constValueIndex)).getValue();
                staticVars.setInt(field.getSlotID(), shortVal);
                break;
            case 'Z':
                int booleanVal = ((IntWrapper) runtimeConstantPool.getConstant(constValueIndex)).getValue();
                staticVars.setInt(field.getSlotID(), booleanVal);
                break;
            default:
                break;
        }
    }

    /**
     * 给不带有final修饰符的静态字段赋零值
     * @param jClass 进行准备阶段的类
     * @param field 该类的其中一个静态字段
     */
    private void loadDefaultValue(JClass jClass, Field field) {
        StaticVars staticVars = jClass.getStaticVars();
            switch (field.descriptor.charAt(0)) {
                case 'B':
                    staticVars.setInt(field.getSlotID(), 0);
                    break;
                case 'C':
                    staticVars.setInt(field.getSlotID(), 0);
                    break;
                case 'D':
                    staticVars.setDouble(field.getSlotID(), 0.0d);
                    break;
                case 'F':
                    staticVars.setFloat(field.getSlotID(), 0.0f);
                    break;
                case 'I':
                    staticVars.setInt(field.getSlotID(), 0);
                    break;
                case 'J':
                    staticVars.setLong(field.getSlotID(), 0L);
                    break;
                case 'S':
                    staticVars.setInt(field.getSlotID(), 0);
                    break;
                case 'Z':
                    staticVars.setInt(field.getSlotID(), 0);
                    break;
                case 'L':
                    staticVars.setObject(field.getSlotID(), new NullObject());
                    break;
                default:
                    break;
        }
    }

    /**
     * 此方法用于计算进行类加载的这个类中实例变量所需要的槽的个数
     * 设置了jClass的instanceSlotCount和实例变量在槽中的索引
     * @param jClass 进行类加载的类
     */
    private void calInstanceFieldSlotIDs(JClass jClass) {
        Field[] fields = jClass.getFields();
        int slotID = 0;
        if (jClass.getSuperClass() != null) {
            slotID = jClass.getSuperClass().getInstanceSlotCount();
        }
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].isStatic()) {
                fields[i].setSlotID(slotID);
                slotID++;
                if (fields[i].isDoubleOrLong()) {
                    slotID++;
                }
            }
        }
        jClass.setInstanceSlotCount(slotID);
    }

}
