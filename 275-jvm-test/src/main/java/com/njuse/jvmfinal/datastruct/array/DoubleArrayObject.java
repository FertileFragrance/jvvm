package com.njuse.jvmfinal.datastruct.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;

@Getter
public class DoubleArrayObject extends ArrayObject {

    private double[] array;

    public DoubleArrayObject(JClass jClass, int length, int type) {
        super(jClass, length, type);
        array = new double[length];
    }

}
