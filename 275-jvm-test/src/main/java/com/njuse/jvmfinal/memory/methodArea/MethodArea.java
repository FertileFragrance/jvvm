package com.njuse.jvmfinal.memory.methodArea;

import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import lombok.Getter;

import java.util.LinkedHashMap;

@Getter
/**
 * 此类表示方法区，记录已经加载过的类
 * 注意运行时常量池也是方法区的一部分，但它在jclass包下
 */
public class MethodArea {

    /**
     * 采用懒汉式单例模式
     */
    private static MethodArea methodArea = new MethodArea();

    private MethodArea() {
        classNameToClass = new LinkedHashMap<>();
        classToRuntimeConstantPool = new LinkedHashMap<>();
        classToStaticVars = new LinkedHashMap<>();
    }

    public static MethodArea getInstance() {
        return methodArea;
    }

    private LinkedHashMap<String, JClass> classNameToClass;                         /**建立类名到类的映射*/
    private LinkedHashMap<JClass, RuntimeConstantPool> classToRuntimeConstantPool;  /**建立类到运行时常量池的映射*/
    private LinkedHashMap<JClass, StaticVars> classToStaticVars;                    /**建立类到静态变量的映射*/

    /**
     * 尝试在方法区寻找某个类
     * @param className 要找的类的类名
     * @return 能找到则返回查找结果，否则返回空
     */
    public JClass findClass(String className) {
        if (classNameToClass.keySet().stream().anyMatch(name -> name.equals(className))) {
            return classNameToClass.get(className);
        } else {
            return null;
        }
    }

    /**
     * 向方法区添加已经完成类加载连接之前的阶段的类
     * @param className 要添加的类的类名
     * @param jClass 这个类的实例
     */
    public void addClass(String className, JClass jClass) {
        classNameToClass.put(className, jClass);
        classToRuntimeConstantPool.put(jClass, jClass.getRuntimeConstantPool());
    }

    /**
     * 向方法区添加准备阶段完成的类的静态变量
     * @param jClass 要添加的类的实例
     */
    public void addStaticVars(JClass jClass) {
        classToStaticVars.put(jClass, jClass.getStaticVars());
    }

}
