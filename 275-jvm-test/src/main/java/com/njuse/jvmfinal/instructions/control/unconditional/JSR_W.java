package com.njuse.jvmfinal.instructions.control.unconditional;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.BranchInstruction;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

import java.nio.ByteBuffer;

public class JSR_W extends BranchInstruction {

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
     * 将此指令的下一条指令的地址push到操作数栈中，并跳转到由偏移量确定的地址上继续执行
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        // TODO 将此指令的下一条指令的地址push到操作数栈中
        int branchPC = topStackFrame.getNextPC() - 3 + this.offset;
        topStackFrame.setNextPC(branchPC);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\n";
    }
}
