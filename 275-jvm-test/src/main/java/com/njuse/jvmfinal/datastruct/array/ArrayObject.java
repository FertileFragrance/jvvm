package com.njuse.jvmfinal.datastruct.array;

import com.njuse.jvmfinal.datastruct.JObject;
import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;

@Getter
/**
 * 所有数组类的父类
 */
public abstract class ArrayObject extends JObject {

    protected int length;   /**数组的长度*/
    protected int type;     /**数组的类型*/

    public ArrayObject(JClass jClass, int length, int type) {
        this.jClass = jClass;
        this.length = length;
        this.type = type;
    }

}
