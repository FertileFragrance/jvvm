package com.njuse.jvmfinal.memory;

import com.njuse.jvmfinal.datastruct.JObject;

import java.util.HashSet;

/**
 * 此类表示堆，用于存储JObject对象
 */
public class JHeap {

    /**
     * 采用懒汉式单例模式
     */
    private static JHeap jHeap = new JHeap();

    private JHeap() {
        jObjectSet = new HashSet<>();
    }

    public static JHeap getInstance() {
        return jHeap;
    }

    private HashSet<JObject> jObjectSet;    /**存储JObject对象的集合*/

    /**
     * 向堆中添加实例对象
     * @param jObject 要添加的对象
     */
    public void addObject(JObject jObject) {
        jObjectSet.add(jObject);
    }

}
