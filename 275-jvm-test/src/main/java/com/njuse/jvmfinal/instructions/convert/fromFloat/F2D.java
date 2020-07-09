package com.njuse.jvmfinal.instructions.convert.fromFloat;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class F2D extends NoOperandsInstruction {

    /**
     * 从操作数栈中取一个float类型的数转换成double类型，压入操作数栈
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        float val = operandStack.popFloat();
        operandStack.pushDouble((double) val);
    }

}
