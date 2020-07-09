package com.njuse.jvmfinal.memory.jclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ClassMember { // 有且仅有Field和Method继承该抽象类

    public short accessFlags;       // 字段或方法的访问标志
    public String name;             // 字段或方法的名称
    public String descriptor;       // 字段或方法的描述符号
    public JClass clazz;            // 字段或方法所在的类

    /**
     * 根据字段和方法共有的访问标志判断是否字段或方法是否被某个修饰符修饰
     * @return 是或否
     */
    public boolean isPublic() {
        return (this.accessFlags & AccessFlags.ACC_PUBLIC) != 0;
    }

    public boolean isPrivate() {
        return (this.accessFlags & AccessFlags.ACC_PRIVATE) != 0;
    }

    public boolean isProtected() {
        return (this.accessFlags & AccessFlags.ACC_PROTECTED) != 0;
    }

    public boolean isStatic() {
        return (this.accessFlags & AccessFlags.ACC_STATIC) != 0;
    }

    public boolean isFinal() {
        return (this.accessFlags & AccessFlags.ACC_FINAL) != 0;
    }

    public boolean isSynthetic() {
        return (this.accessFlags & AccessFlags.ACC_SYNTHETIC) != 0;
    }

}
