package com.njuse.jvmfinal.instructions.control.unconditional;

import com.njuse.jvmfinal.instructions.abstractIns.BranchInstruction;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

import java.nio.ByteBuffer;

public class GOTO_W extends BranchInstruction {

    /**
     * 要读取四个字节，构建偏移量为(byte1 << 24) | (byte2 << 16) | (byte3 << 8) | byte4
     * 相当于读了一个int类型的数
     * @param codeReader 读码器
     */
    @Override
    public void fetchOperands(ByteBuffer codeReader) {
        this.offset = codeReader.getInt();
    }

    /**
     * 直接跳转到由偏移量确定的地址上继续执行
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        int branchPC = topStackFrame.getNextPC() - 3 + this.offset;
        topStackFrame.setNextPC(branchPC);
    }
}
