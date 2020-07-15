package com.njuse.jvmfinal.datastruct.array;

import lombok.Getter;

@Getter
public class FloatArrayObject extends ArrayObject {

    private float[] array;

    public FloatArrayObject(int length, int type) {
        super(length, type);
        array = new float[length];
    }

}
