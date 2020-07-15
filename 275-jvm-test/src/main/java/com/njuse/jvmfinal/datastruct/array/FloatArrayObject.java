package com.njuse.jvmfinal.datastruct.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;

@Getter
public class FloatArrayObject extends ArrayObject {

    private float[] array;

    public FloatArrayObject(JClass jClass, int length, int type) {
        super(jClass, length, type);
        array = new float[length];
    }

}
