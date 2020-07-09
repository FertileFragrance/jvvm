package com.njuse.jvmfinal.instructions.operate.neg;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class FNEG extends NoOperandsInstruction {

    /**
     * 从操作数栈中取一个float类型的数值，取它的相反数后push到操作数栈栈顶
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        float val = operandStack.popFloat();
        operandStack.pushFloat(-val);
    }

}
