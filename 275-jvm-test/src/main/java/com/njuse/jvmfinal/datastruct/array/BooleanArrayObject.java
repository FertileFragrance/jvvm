package com.njuse.jvmfinal.datastruct.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;

@Getter
public class BooleanArrayObject extends ArrayObject {

    private boolean[] array;

    public BooleanArrayObject(JClass jClass, int length, int type) {
        super(jClass, length, type);
        array = new boolean[length];
    }

}
