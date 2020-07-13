package com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.InterfaceMethodrefInfo;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import lombok.Getter;

@Getter
public class InterfaceMethodRef extends MemberRef {

    private Method interfaceMethodToResolve;    // 解析完成的方法

    public InterfaceMethodRef(RuntimeConstantPool runtimeConstantPool, InterfaceMethodrefInfo interfaceMethodrefInfo) {
        super(runtimeConstantPool, interfaceMethodrefInfo);
        //method
    }

    /**
     * 接口方法解析的入口，解析之前先要对方法所属的类或接口的符号引用进行解析
     * @return 解析好的接口方法
     */
    public Method resolveInterfaceMethodRef() {
        if (interfaceMethodToResolve != null) {
            return interfaceMethodToResolve;
        }
        resolveClassRef();
        interfaceMethodToResolve = resolveInterfaceMethodRefInSuperClass(classToResolve, name, descriptor);
        return interfaceMethodToResolve;
    }

    private Method resolveInterfaceMethodRefInSuperClass(JClass jClass, String name, String descriptor) {
        if (jClass == null || jClass.getName().equals("")) {
            return null;
        }
        for (Method m : jClass.getMethods()) {
            if (m.getName().equals(name) && m.getDescriptor().equals(descriptor)) {
                return m;
            }
        }
        if (resolveInterfaceMethodRefInInterfaces(jClass, name, descriptor) != null) {
            return resolveInterfaceMethodRefInInterfaces(jClass, name, descriptor);
        }
        return resolveInterfaceMethodRefInSuperClass(jClass.getSuperClass(), name, descriptor);
    }

    private Method resolveInterfaceMethodRefInInterfaces(JClass jClass, String name, String descriptor) {
        JClass[] interfaces = jClass.getInterfaces();
        for (JClass clazz : interfaces) {
            for (Method m : clazz.getMethods()) {
                if (m.getName().equals(name) && m.getDescriptor().equals(descriptor)) {
                    return m;
                }
            }
        }
        return null;
    }

    /**
     * 通过对象引用所在的类找到要被调用的接口方法
     * @param jClass 对象引用所在的类
     * @return 找到的接口方法
     */
    public Method resolveInterfaceMethodRef(JClass jClass) {
        return resolveInterfaceMethodRefInSuperClass(jClass, name, descriptor);
    }

}
