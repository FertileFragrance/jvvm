package com.njuse.jvmfinal.instructions.control.unconditional;

import com.njuse.jvmfinal.instructions.abstractIns.BranchInstruction;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class JSR extends BranchInstruction {

    /**
     * 将此指令的下一条指令的地址push到操作数栈中，并跳转到由偏移量确定的地址上继续执行
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        // TODO 将此指令的下一条指令的地址push到操作数栈中
        int branchPC = topStackFrame.getNextPC() - 3 + this.offset;
        topStackFrame.setNextPC(branchPC);
    }

}
