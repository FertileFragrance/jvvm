package com.njuse.jvmfinal.datastruct.array;

import lombok.Getter;

@Getter
public class LongArrayObject extends ArrayObject {

    private long[] array;

    public LongArrayObject(int length, int type) {
        super(length, type);
        array = new long[length];
    }

}
