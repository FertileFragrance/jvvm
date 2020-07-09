package com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.FieldrefInfo;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import lombok.Getter;

@Getter
public class FieldRef extends MemberRef {
    private Field fieldToResolve;    // 解析完成的字段

    public FieldRef(RuntimeConstantPool runtimeConstantPool, FieldrefInfo fieldrefInfo) {
        super(runtimeConstantPool, fieldrefInfo);
    }

    /**
     * 字段解析的入口，解析之前先要对字段所属的类或接口的符号引用进行解析
     * @return 解析好的字段
     */
    public Field resolveFieldRef() {
        if (fieldToResolve != null) {
            return fieldToResolve;
        }
        resolveClassRef();
        fieldToResolve = resolveFieldRefInInterfacesAndSuperClass(classToResolve, name, descriptor);
        return fieldToResolve;
    }

    /**
     * 递归解析字段
     * @param jClass 要查找的类
     * @param name 字段名
     * @param descriptor 字段描述符
     * @return 解析好的字段
     */
    private Field resolveFieldRefInInterfacesAndSuperClass(JClass jClass, String name, String descriptor) {
        for (Field f : jClass.getFields()) {
            if (f.getName().equals(name) && f.getDescriptor().equals(descriptor)) {
                return f;
            }
        }
        for (JClass clazz : jClass.getInterfaces()) {
            Field field = resolveFieldRefInInterfacesAndSuperClass(clazz, name, descriptor);
            if (field != null) {
                return field;
            }
        }
        if (jClass.getSuperClass() != null) {
            return resolveFieldRefInInterfacesAndSuperClass(jClass.getSuperClass(), name, descriptor);
        }
        return null;
    }

}
