package com.njuse.jvmfinal.datastruct.array;

import lombok.Getter;

@Getter
public class BooleanArrayObject extends ArrayObject {

    private boolean[] array;

    public BooleanArrayObject(int length, int type) {
        super(length, type);
        array = new boolean[length];
    }

}
