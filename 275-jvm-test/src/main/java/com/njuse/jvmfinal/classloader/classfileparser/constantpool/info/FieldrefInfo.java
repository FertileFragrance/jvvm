package com.njuse.jvmfinal.classloader.classfileparser.constantpool.info;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.ConstantPool;
import org.apache.commons.lang3.tuple.Pair;

public class FieldrefInfo extends MemberRefInfo {   // 只是常量池某个项目的结构，不是字段表
    private int classIndex;                         // 指向声明字段的类描述符CONSTANT_Class_info的索引项
    private int nameAndTypeIndex;                   // 指向字段描述符CONSTANT_NameAndType的索引项

    public FieldrefInfo(ConstantPool constantPool, int classIndex, int nameAndTypeIndex) {
        super(constantPool);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
        super.tag = ConstantPoolInfo.FIELD_REF;
    }

    /*
        由类的索引得到字符串表示的类的名字
     */
    public String getClassName() {
        return getClassName(classIndex);
    }

    /*
        由描述符的索引得到字符串类型的字段名和字段描述符
     */
    public Pair<String, String> getNameAndDescriptor() {
        return ((NameAndTypeInfo) myCP.get(nameAndTypeIndex)).getNameAndDescriptor();
    }

}
