package com.njuse.jvmfinal.classloader.classfileparser.constantpool.info;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.ConstantPool;

public class ClassInfo extends ConstantPoolInfo {
    private int nameIndex;

    public ClassInfo(ConstantPool constantPool, int nameIndex) {
        super(constantPool);
        this.nameIndex = nameIndex;
        super.tag = ConstantPoolInfo.CLASS;
    }

    /*
        获取字符串类型的ClassName
     */
    public String getClassName() {
        return ((UTF8Info) myCP.get(nameIndex)).getString();

    }

    @Override
    public String toString() {
        return getClassName();
    }
}
