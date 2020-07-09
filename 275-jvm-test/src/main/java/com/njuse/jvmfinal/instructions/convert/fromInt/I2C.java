package com.njuse.jvmfinal.instructions.convert.fromInt;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class I2C extends NoOperandsInstruction {

    /**
     * 从操作数栈中取一个int类型的数转换成char类型，再符号扩展回int类型压入操作数栈
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        int val = operandStack.popInt();
        operandStack.pushInt((char) val);
    }

}
