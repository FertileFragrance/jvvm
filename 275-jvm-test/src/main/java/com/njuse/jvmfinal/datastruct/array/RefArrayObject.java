package com.njuse.jvmfinal.datastruct.array;

import com.njuse.jvmfinal.datastruct.JObject;
import lombok.Getter;

@Getter
public class RefArrayObject extends ArrayObject {

    private JObject[] array;

    public RefArrayObject(int length, int type) {
        super(length, type);
        array = new JObject[length];
    }

}
