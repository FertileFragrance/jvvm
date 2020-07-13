package com.njuse.jvmfinal.instructions.control.unconditional;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.BranchInstruction;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class GOTO extends BranchInstruction {

    /**
     * 直接跳转到由偏移量确定的地址上继续执行
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        int branchPC = topStackFrame.getNextPC() - 3 + this.offset;
        topStackFrame.setNextPC(branchPC);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\n";
    }

}
