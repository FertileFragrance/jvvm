package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;

/**
 * 所有store_<n>指令的父类
 */
public abstract class STORE_N extends NoOperandsInstruction {

    protected int index;    /**当前栈帧局部变量表的索引值，调用构造函数时就确定下来了*/

    @Override
    public String toString() {
        String suffix = index + "";
        return this.getClass().getSimpleName().replace("N", suffix);
    }

}
