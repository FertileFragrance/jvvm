package com.njuse.jvmfinal.classloader.classfilereader.classpath;

import lombok.Getter;

@Getter
/**
 * 此类用于决定搜索路径的类型，可把它当作双亲委派机制的三种加载器
 */
public class EntryType {

    private int value;  /**值，当这个值分别达到一定数量时就会有更高的搜索权限*/

    public EntryType(int value) {
        this.value = value;
    }

    public static final int BOOT_ENTRY = 1;
    public static final int EXT_ENTRY = 3;
    public static final int USER_ENTRY = 7;

}
