package com.njuse.jvmfinal.datastruct.array;

import lombok.Getter;

@Getter
public class ShortArrayObject extends ArrayObject {

    private short[] array;

    public ShortArrayObject(int length, int type) {
        super(length, type);
        array = new short[length];
    }

}
