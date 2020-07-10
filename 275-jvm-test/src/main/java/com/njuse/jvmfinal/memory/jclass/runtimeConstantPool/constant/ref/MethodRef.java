package com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.MethodrefInfo;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;

public class MethodRef extends MemberRef {

    private Method methodToResolve; // 解析完成的方法

    public MethodRef(RuntimeConstantPool runtimeConstantPool, MethodrefInfo methodrefInfo) {
        super(runtimeConstantPool, methodrefInfo);
    }

    /**
     * 方法解析的入口，解析之前先要对方法所属的类或接口的符号引用进行解析
     * @return 解析好的方法
     */
    public Method resolveMethodRef() {
        if (methodToResolve != null) {
            return methodToResolve;
        }
        resolveClassRef();
        methodToResolve = resolveMethodRefInSuperClass(classToResolve, name, descriptor);
        return methodToResolve;
    }

    /**
     * 递归解析方法
     * @param jClass 要查找的类
     * @param name 方法名
     * @param descriptor 方法描述符
     * @return 解析好的方法
     */
    private Method resolveMethodRefInSuperClass(JClass jClass, String name, String descriptor) {
        for (Method m : jClass.getMethods()) {
            if (m.getName().equals(name) && m.getDescriptor().equals(descriptor)) {
                return m;
            }
        }
        if (jClass.getSuperClass() != null) {
            return resolveMethodRefInSuperClass(jClass.getSuperClass(), name, descriptor);
        }
        return null;
    }

    /**
     * 通过对象引用所在的类找到要被调用的方法
     * @param jClass 对象引用所在的类
     * @return 找到的方法
     */
    public Method resolveMethodRef(JClass jClass) {
        for (Method m : jClass.getMethods()) {
            if (m.getName().equals(name) && m.getDescriptor().equals(descriptor)) {
                return m;
            }
        }
        if (jClass.getSuperClass() != null) {
            return resolveMethodRef(jClass.getSuperClass());
        }
        return null;
    }

}
