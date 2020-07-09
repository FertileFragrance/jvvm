package com.njuse.jvmfinal.instructions.abstractIns;

import java.nio.ByteBuffer;

/**
 * 所有读两位字节且用于索引的指令的父类
 */
public abstract class Index16Instruction extends Instruction {

    protected int index;    /**局部变量表或运行时常量池对应的下标*/

    /**
     * 默认读取两个字节获得索引，索引是无符号的
     * @param codeReader 读码器
     */
    @Override
    public void fetchOperands(ByteBuffer codeReader) {
        index = (int) codeReader.getShort() & 0xffff;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " index: " + (index & 0xffff);
    }

}
