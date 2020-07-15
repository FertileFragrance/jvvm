package com.njuse.jvmfinal.datastruct.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;

@Getter
public class ByteArrayObject extends ArrayObject {

    private byte[] array;

    public ByteArrayObject(JClass jClass, int length, int type) {
        super(jClass, length, type);
        array = new byte[length];
    }

}
