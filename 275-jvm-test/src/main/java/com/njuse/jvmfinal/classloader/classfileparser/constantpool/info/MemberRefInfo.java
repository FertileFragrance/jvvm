package com.njuse.jvmfinal.classloader.classfileparser.constantpool.info;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.ConstantPool;
import org.apache.commons.lang3.tuple.Pair;

public abstract class MemberRefInfo extends ConstantPoolInfo {

    public MemberRefInfo(ConstantPool myCP) {
        super(myCP);
    }


    public abstract String getClassName();

    /*
        通过idx获取常量池中的第idx项，再强转为ClassInfo类型，由ClassInfo类型获取到的ClassName即为字符串
     */
    protected String getClassName(int idx) {
        return ((ClassInfo) myCP.get(idx)).getClassName();
    }


    public abstract Pair<String, String> getNameAndDescriptor();
}
