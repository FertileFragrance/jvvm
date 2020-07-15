package com.njuse.jvmfinal.datastruct;

import com.njuse.jvmfinal.memory.jclass.JClass;

/**
 * 此抽象类表示各种可能形式的对象
 */
public abstract class JObject {

    protected JClass jClass;    /**该对象真正所属的类*/

    public JClass getClazz() {
        return jClass;
    }

    /**
     * 判断此jObject对象是否是某个类的实例
     * @param T 某个类
     * @return 是或否
     */
    public boolean isInstanceOf(JClass T) {
        JClass S = this.getClazz();
        if (!S.isArray()) {
            if (!S.isInterface()) {
                if (!T.isInterface()) {
                    return S == T || S.isSubClassOf(T);
                } else {
                    return S.isImplementOf(T);
                }
            } else {
                if (!T.isInterface()) {
                    return T.getName().equals("java/lang/Object");
                } else {
                    return S == T || S.isImplementOf(T);
                }
            }
        } else {
            if (!T.isArray()) {
                if (!T.isInterface()) {
                    return T.getName().equals("java/lang/Object");
                } else {
                    return T.getName().equals("java/java/io/Serializable") || T.getName().equals("java/lang/Cloneable");
                }
            } else {
                return S.getPrimitiveType().equals(T.getPrimitiveType());
            }
        }
    }

}
