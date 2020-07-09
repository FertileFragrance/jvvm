package com.njuse.jvmfinal.instructions.abstractIns;

import java.nio.ByteBuffer;

/**
 * 所有分支跳转指令的父类
 */
public abstract class BranchInstruction extends Instruction {

    protected int offset;   /**PC的偏移量*/

    /**
     * 默认读取两个字节构造偏移量，偏移量是有符号的
     * @param codeReader 读码器
     */
    @Override
    public void fetchOperands(ByteBuffer codeReader) {
        offset = codeReader.getShort();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " offset: " + offset;
    }

}
