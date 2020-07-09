package com.njuse.jvmfinal.classloader.classfilereader.classpath;

/**
 * 有四个类继承此抽象类，用于读取四种路径格式的class文件
 */
public abstract class Entry {

    protected String className;
    protected String classPath;

    public Entry(String className, String classPath) {
        this.className = className;
        this.classPath = classPath;
    }

    public abstract byte[] readClassFile(String className, String classPath);

    public String getClassPath() {
        return classPath;
    }
}
