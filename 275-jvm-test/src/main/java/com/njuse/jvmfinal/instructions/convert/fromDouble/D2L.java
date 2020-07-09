package com.njuse.jvmfinal.instructions.convert.fromDouble;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class D2L extends NoOperandsInstruction {

    /**
     * 从操作数栈中取一个double类型的数转换成long类型，压入操作数栈
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        double val = operandStack.popDouble();
        operandStack.pushLong((long) val);
    }

}
