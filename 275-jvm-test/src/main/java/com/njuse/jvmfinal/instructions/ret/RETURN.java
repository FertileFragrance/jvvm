package com.njuse.jvmfinal.instructions.ret;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;
import com.njuse.jvmfinal.memory.threadStack.ThreadStack;

public class RETURN extends NoOperandsInstruction {

    /**
     * 将顶层栈帧弹出线程栈，不返回任何值
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        ThreadStack threadStack = topStackFrame.getThreadStack();
        threadStack.popStackFrame();
    }

}
