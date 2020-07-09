package com.njuse.jvmfinal.classloader.classfilereader.classpath;

import com.njuse.jvmfinal.util.IOUtil;

import java.io.*;
import java.util.zip.*;

/**
 * 此类继承Entry类，用于寻找通配符下的class文件并读取
 */
public class WildEntry extends Entry {

    public WildEntry(String className, String classPath) {
        super(className, classPath);
    }

    @Override
    public byte[] readClassFile(String className, String classPath) {
        /**
         * 获得通配符之前的路径
         */
        StringBuffer sb = new StringBuffer();
        for (int i = 0; classPath.charAt(i) != '*'; i++) {
            sb.append(classPath.charAt(i));
        }
        String basePath = sb.toString();

        /**
         * 先按照相对路径寻找
         */
        File dirFile = new File(IOUtil.transform(System.getProperty("user.dir") +
                File.separator + IOUtil.transform(classPath) + File.separator + IOUtil.transform(className)));
        FileInputStream fis;
        try {
            fis = new FileInputStream(dirFile);
            return IOUtil.readFileByBytes(fis);
        } catch (IOException e) {}

        /**
         * 相对路径找不到按照归档文件去找
         */
        File file = new File(basePath);
        String[] folder = file.list();
        ZipFile zipFile;
        for(int i = 0; i < folder.length; i++) {
            try {
                zipFile = new ZipFile(basePath + folder[i]);
                if(zipFile != null) {
                    ZipEntry zipEntry = zipFile.getEntry(IOUtil.transform(className));
                    if(zipEntry != null) {
                        return IOUtil.readFileByBytes(zipFile.getInputStream(zipEntry));
                    }
                }
            } catch (IOException e) {}
        }
        return null;
    }
}
