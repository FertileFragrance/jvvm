package com.njuse.jvmfinal.classloader.classfileparser;

import com.njuse.jvmfinal.classloader.classfileparser.attribute.AttributeInfo;
import com.njuse.jvmfinal.classloader.classfileparser.attribute.ConstantValueAttr;
import com.njuse.jvmfinal.classloader.classfileparser.constantpool.ConstantPool;
import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.UTF8Info;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

public class FieldInfo {                // 真正的字段表
    private short accessFlags;          // 访问标志
    private short nameIndex;            // 字段名的索引
    private String name;                // 字段名
    private short descriptorIndex;      // 字段描述符的索引
    private String descriptor;          // 字段描述符（用于描述字段的数据类型）
    private short attributesCount;      // 属性表的数量
    private AttributeInfo[] attributes; // 属性表集合

    /*
        构造方法，完成了所有成员变量的初始化
     */
    public FieldInfo(ConstantPool constantPool, Supplier<AttributeInfo> attributeBuilder, ByteBuffer in) {
        this.accessFlags = in.getShort();
        this.nameIndex = in.getShort();
        this.descriptorIndex = in.getShort();
        this.attributesCount = in.getShort();
        this.attributes = new AttributeInfo[this.attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            this.attributes[i] = attributeBuilder.get();
        }
        this.name = ((UTF8Info) constantPool.get(this.nameIndex)).getString();
        this.descriptor = ((UTF8Info) constantPool.get(this.descriptorIndex)).getString();
    }


    public short getAccessFlags() {
        return accessFlags;
    }


    public String getName() {
        return name;
    }


    public String getDescriptor() {
        return descriptor;
    }


    public ConstantValueAttr getConstantValueAttr() {
        for (AttributeInfo attribute : attributes) {
            if (attribute instanceof ConstantValueAttr) {
                return (ConstantValueAttr) attribute;
            }
        }
        return null;
    }
}
