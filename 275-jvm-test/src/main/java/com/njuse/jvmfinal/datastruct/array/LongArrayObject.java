package com.njuse.jvmfinal.datastruct.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;

@Getter
public class LongArrayObject extends ArrayObject {

    private long[] array;

    public LongArrayObject(JClass jClass, int length, int type) {
        super(jClass, length, type);
        array = new long[length];
    }

}
