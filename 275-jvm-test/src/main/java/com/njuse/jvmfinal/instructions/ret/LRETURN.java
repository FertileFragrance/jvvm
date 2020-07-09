package com.njuse.jvmfinal.instructions.ret;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;
import com.njuse.jvmfinal.memory.threadStack.ThreadStack;

public class LRETURN extends NoOperandsInstruction {

    /**
     * 从顶层栈帧的操作数栈中pop一个long类型的值作为返回值，这个栈帧弹出后将此值压入新的顶层栈帧的操作数栈的栈顶
     * @param topStackFrame 原来的顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        ThreadStack threadStack = topStackFrame.getThreadStack();
        OperandStack operandStack = topStackFrame.getOperandStack();
        threadStack.popStackFrame();
        threadStack.getTopStackFrame().getOperandStack().pushLong(operandStack.popLong());
    }

}
