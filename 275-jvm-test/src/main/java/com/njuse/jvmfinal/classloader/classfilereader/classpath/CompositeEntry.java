package com.njuse.jvmfinal.classloader.classfilereader.classpath;

import com.njuse.jvmfinal.classloader.classfilereader.ClassFileReader;
import com.njuse.jvmfinal.util.IOUtil;

import java.io.*;
import java.util.zip.*;

/**
 * 此类继承Entry类，实现了复合型命令对class文件的读取
 */
public class CompositeEntry extends Entry {

    public CompositeEntry(String className, String classPath) {
        super(className, classPath);
    }

    @Override
    public byte[] readClassFile(String className, String classPath) {
        String[] path = classPath.split(File.pathSeparator);
        for (int i = 0; i < path.length; i++) {
            if (ClassFileReader.chooseEntryType(className, path[i]) instanceof DirEntry) {
                FileInputStream fis;
                byte[] res = null;
                File file = new File(IOUtil.transform(System.getProperty("user.dir") +
                        File.separator + IOUtil.transform(path[i]) + File.separator + IOUtil.transform(className)));
                try {
                    fis = new FileInputStream(file);
                    res = IOUtil.readFileByBytes(fis);
                    fis.close();
                } catch (FileNotFoundException e) {
                } catch (IOException e) {}
                if (res != null) {
                    return res;
                }
            } else if (ClassFileReader.chooseEntryType(className, path[i]) instanceof ArchivedEntry) {
                ZipFile zipFile;
                try {
                    zipFile = new ZipFile(IOUtil.transform(path[i]));
                    ZipEntry zipEntry = zipFile.getEntry(IOUtil.transform(className));
                    if (zipEntry != null) {
                        return IOUtil.readFileByBytes(zipFile.getInputStream(zipEntry));
                    }
                } catch (IOException e) {}
            } else {
                StringBuffer sb = new StringBuffer();
                for (int j = 0; classPath.charAt(j) != '*'; j++) {
                    sb.append(classPath.charAt(j));
                }
                String basePath = sb.toString();

                File dirFile = new File(IOUtil.transform(System.getProperty("user.dir") +
                        File.separator + IOUtil.transform(path[i]) + File.separator + IOUtil.transform(className)));
                FileInputStream fis;
                try {
                    fis = new FileInputStream(dirFile);
                    return IOUtil.readFileByBytes(fis);
                } catch (IOException e) {}

                File file = new File(basePath);
                String[] folder = file.list();
                if (folder == null) {
                    continue;
                }
                ZipFile zipFile;
                for(int j = 0; j < folder.length; j++) {
                    try {
                        zipFile = new ZipFile(basePath + folder[j]);
                        if(zipFile != null) {
                            ZipEntry zipEntry = zipFile.getEntry(IOUtil.transform(className));
                            if(zipEntry != null) {
                                return IOUtil.readFileByBytes(zipFile.getInputStream(zipEntry));
                            }
                        }
                    } catch (IOException e) {}
                }
            }
        }
        return null;
    }
}
