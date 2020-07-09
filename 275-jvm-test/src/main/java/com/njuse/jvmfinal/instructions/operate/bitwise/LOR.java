package com.njuse.jvmfinal.instructions.operate.bitwise;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class LOR extends NoOperandsInstruction {

    /**
     * 从操作数栈中依次获取两个long类型的数，做按位或运算后将结果push到操作数栈栈顶
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        long val2 = operandStack.popLong();
        long val1 = operandStack.popLong();
        long res = val1 | val2;
        operandStack.pushLong(res);
    }

}
