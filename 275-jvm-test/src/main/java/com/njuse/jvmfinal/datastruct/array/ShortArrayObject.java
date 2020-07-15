package com.njuse.jvmfinal.datastruct.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;

@Getter
public class ShortArrayObject extends ArrayObject {

    private short[] array;

    public ShortArrayObject(JClass jClass, int length, int type) {
        super(jClass, length, type);
        array = new short[length];
    }

}
