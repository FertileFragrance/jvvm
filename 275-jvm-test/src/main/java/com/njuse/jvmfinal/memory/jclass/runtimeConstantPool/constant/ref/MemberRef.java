package com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.MemberRefInfo;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

@Getter
public abstract class MemberRef extends SymRef {
    protected String name;          // 字段名或方法名
    protected String descriptor;    // 字段或方法的描述符

    public MemberRef(RuntimeConstantPool runtimeConstantPool, MemberRefInfo info) {
        this.runtimeConstantPool = runtimeConstantPool;
        this.className = info.getClassName();
        Pair<String, String> nameAndType = info.getNameAndDescriptor();
        this.name = nameAndType.getKey();
        this.descriptor = nameAndType.getValue();
    }

}
