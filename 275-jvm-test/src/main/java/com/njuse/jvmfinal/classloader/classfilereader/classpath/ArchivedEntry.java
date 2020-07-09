package com.njuse.jvmfinal.classloader.classfilereader.classpath;

import com.njuse.jvmfinal.util.IOUtil;

import java.io.*;
import java.util.zip.*;

/**
 * 此类继承Entry类，用于读取归档文件中的class文件
 */
public class ArchivedEntry extends Entry {

    public ArchivedEntry(String className, String classPath) {
        super(className, classPath);
    }

    @Override
    public byte[] readClassFile(String className, String classPath) {
        ZipFile zipFile;
        byte[] res;
        try {
            zipFile = new ZipFile(IOUtil.transform(classPath));
            ZipEntry zipEntry = zipFile.getEntry(IOUtil.transform(className));
            if (zipEntry == null) {
                return null;
            }
            res = IOUtil.readFileByBytes(zipFile.getInputStream(zipEntry));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }
}
