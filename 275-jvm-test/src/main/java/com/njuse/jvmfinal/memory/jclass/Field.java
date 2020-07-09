package com.njuse.jvmfinal.memory.jclass;

import com.njuse.jvmfinal.classloader.classfileparser.FieldInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Field extends ClassMember {
    private int slotID;             // 局部变量表或方法区静态变量槽的索引，类加载时获取，这是字段与字段引用差别的关键所在
    private int constValueIndex;    // 若为final static型字段，它指向该最终常量，否则为空，创建Field对象时获取

    public Field(FieldInfo info, JClass clazz) {
        this.clazz = clazz;
        accessFlags = info.getAccessFlags();
        name = info.getName();
        descriptor = info.getDescriptor();
        if (info.getConstantValueAttr() != null) {
            constValueIndex = info.getConstantValueAttr().getConstantValueIndex();
        }
    }

    /**
     * 根据字段访问标志或描述符判断该字段是否被某个修饰符修饰
     * @return 是或否
     */
    public boolean isVolatile() {
        return (this.accessFlags & AccessFlags.ACC_VOLATILE) != 0;
    }

    public boolean isTransient() {
        return (this.accessFlags & AccessFlags.ACC_TRANSIENT) != 0;
    }

    public boolean isEnum() {
        return (this.accessFlags & AccessFlags.ACC_ENUM) != 0;
    }

    public boolean isDoubleOrLong() {
        return descriptor.equals("D") || descriptor.equals("J");
    }

}
