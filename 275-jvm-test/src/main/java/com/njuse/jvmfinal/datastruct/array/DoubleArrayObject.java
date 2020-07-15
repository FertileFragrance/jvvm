package com.njuse.jvmfinal.datastruct.array;

import lombok.Getter;

@Getter
public class DoubleArrayObject extends ArrayObject {

    private double[] array;

    public DoubleArrayObject(int length, int type) {
        super(length, type);
        array = new double[length];
    }

}
