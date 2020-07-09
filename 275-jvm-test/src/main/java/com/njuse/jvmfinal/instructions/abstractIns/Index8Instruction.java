package com.njuse.jvmfinal.instructions.abstractIns;

import java.nio.ByteBuffer;

/**
 * 所有读一位字节且用于索引的指令的父类
 */
public abstract class Index8Instruction extends Instruction {

    protected int index;    /**局部变量表或运行时常量池对应的下标*/

    /**
     * 默认读取一个字节获得索引，索引是无符号的
     * @param codeReader 读码器
     */
    @Override
    public void fetchOperands(ByteBuffer codeReader) {
        index = (int) codeReader.get() & 0xff;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " index: " + (index & 0xff);
    }

}
