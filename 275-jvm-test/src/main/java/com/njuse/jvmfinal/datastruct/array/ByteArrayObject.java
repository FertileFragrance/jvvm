package com.njuse.jvmfinal.datastruct.array;

import lombok.Getter;

@Getter
public class ByteArrayObject extends ArrayObject {

    private byte[] array;

    public ByteArrayObject(int length, int type) {
        super(length, type);
        array = new byte[length];
    }

}
