package com.njuse.jvmfinal.datastruct.array;

import com.njuse.jvmfinal.datastruct.JObject;
import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;

@Getter
public class RefArrayObject extends ArrayObject {

    private JObject[] array;

    public RefArrayObject(JClass jClass, int length, int type) {
        super(jClass, length, type);
        array = new JObject[length];
    }

}
