package com.njuse.jvmfinal.instructions.opndstack;

import com.njuse.jvmfinal.datastruct.Slot;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class DUP2_X2 extends NoOperandsInstruction {

    /**
     * 复制操作数栈栈顶的一个或两个元素但元素的类型一共恰好占两个槽位，再压入操作数栈栈顶以下四个槽位之后
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        Slot val1 = operandStack.popSlot();
        Slot val2 = operandStack.popSlot();
        Slot val3 = operandStack.popSlot();
        Slot val4 = operandStack.popSlot();
        operandStack.pushSlot(val2.clone());
        operandStack.pushSlot(val1.clone());
        operandStack.pushSlot(val4.clone());
        operandStack.pushSlot(val3.clone());
        operandStack.pushSlot(val2.clone());
        operandStack.pushSlot(val1.clone());
    }

}
