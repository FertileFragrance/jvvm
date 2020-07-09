package com.njuse.jvmfinal.instructions.opndstack;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class POP2 extends NoOperandsInstruction {

    /**
     * 将操作数栈栈顶的一个或两个元素出栈且但元素的类型一共恰好占两个槽位
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        operandStack.popSlot();
        operandStack.popSlot();
    }

}
