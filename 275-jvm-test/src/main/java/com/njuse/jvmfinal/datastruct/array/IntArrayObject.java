package com.njuse.jvmfinal.datastruct.array;

import lombok.Getter;

@Getter
public class IntArrayObject extends ArrayObject {

    private int[] array;

    public IntArrayObject(int length, int type) {
        super(length, type);
        array = new int[length];
    }

}
