package com.njuse.jvmfinal.datastruct.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;

@Getter
public class CharArrayObject extends ArrayObject {

    private char[] array;

    public CharArrayObject(JClass jClass, int length, int type) {
        super(jClass, length, type);
        array = new char[length];
    }

}
