package com.njuse.jvmfinal.instructions.abstractIns;

import java.nio.ByteBuffer;

/**
 * 所有不读任何字节的指令的父类
 */
public abstract class NoOperandsInstruction extends Instruction {

    /**
     * 读码器不读取任何字节
     * @param codeReader 读码器
     */
    @Override
    public void fetchOperands(ByteBuffer codeReader) {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
