package com.njuse.jvmfinal.instructions.convert.fromLong;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class L2I extends NoOperandsInstruction {

    /**
     * 从操作数栈中取一个long类型的数转换成int类型，压入操作数栈
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        long val = operandStack.popLong();
        operandStack.pushInt((int) val);
    }

}
