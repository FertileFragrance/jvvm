package com.njuse.jvmfinal.datastruct.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;

@Getter
public class IntArrayObject extends ArrayObject {

    private int[] array;

    public IntArrayObject(JClass jClass, int length, int type) {
        super(jClass, length, type);
        array = new int[length];
    }

}
