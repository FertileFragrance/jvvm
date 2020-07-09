package com.njuse.jvmfinal.classloader.classfilereader.classpath;

import com.njuse.jvmfinal.util.IOUtil;

import java.io.*;

/**
 * 此类继承Entry类，用于读取相对路径格式的class文件
 */
public class DirEntry extends Entry {

    public DirEntry(String className, String classPath) {
        super(className, classPath);
    }

    @Override
    public byte[] readClassFile(String className, String classPath) {
        FileInputStream fis;
        byte[] res;
        File file = new File(IOUtil.transform(System.getProperty("user.dir") +
                File.separator + IOUtil.transform(classPath) + File.separator + IOUtil.transform(className)));
        try {
            fis = new FileInputStream(file);
            res = IOUtil.readFileByBytes(fis);
            fis.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return res;
    }
}
