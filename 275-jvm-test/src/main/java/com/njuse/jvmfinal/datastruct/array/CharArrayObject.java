package com.njuse.jvmfinal.datastruct.array;

import lombok.Getter;

@Getter
public class CharArrayObject extends ArrayObject {

    private char[] array;

    public CharArrayObject(int length, int type) {
        super(length, type);
        array = new char[length];
    }

}
