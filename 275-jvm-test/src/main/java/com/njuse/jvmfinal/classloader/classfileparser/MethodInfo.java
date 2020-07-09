package com.njuse.jvmfinal.classloader.classfileparser;

import com.njuse.jvmfinal.classloader.classfileparser.attribute.AttributeInfo;
import com.njuse.jvmfinal.classloader.classfileparser.attribute.CodeAttribute;
import com.njuse.jvmfinal.classloader.classfileparser.constantpool.ConstantPool;
import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.UTF8Info;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

public class MethodInfo {               // 真正的方法表
    private int accessFlags;            // 访问标志
    private int nameIndex;              // 方法名的索引
    private String name;                // 方法名
    private int descriptorIndex;        // 方法描述符索引
    private String descriptor;          // 方法描述符（用于描述方法的参数列表和返回值类型）
    private int attributesCount;        // 属性表的数量
    private AttributeInfo[] attributes; // 属性表集合
    private CodeAttribute code;         // 方法代码的属性表示，在获取的时候初始化

    /*
        构造方法，完成了除code以外所有成员变量的初始化
     */
    public MethodInfo(ConstantPool constantPool, Supplier<AttributeInfo> attributeBuilder, ByteBuffer in) {
        BuildUtil info = new BuildUtil(in);
        accessFlags = info.getU2();
        nameIndex = info.getU2();
        descriptorIndex = info.getU2();
        attributesCount = info.getU2();
        attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = attributeBuilder.get();
        }
        this.name = ((UTF8Info) constantPool.get(this.nameIndex)).getString();
        this.descriptor = ((UTF8Info) constantPool.get(this.descriptorIndex)).getString();
    }

    public short getAccessFlags() {
        return (short) (accessFlags & 0xFFFF);
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public CodeAttribute getCodeAttribute() {
        if (code == null) {
            for (AttributeInfo attribute : attributes) {
                if (attribute instanceof CodeAttribute) {
                    code = (CodeAttribute) attribute;
                    return code;
                }
            }
        }
        return code;
    }
}
