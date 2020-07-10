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

}
