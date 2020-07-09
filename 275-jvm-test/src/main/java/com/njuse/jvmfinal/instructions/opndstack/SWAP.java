package com.njuse.jvmfinal.instructions.opndstack;

import com.njuse.jvmfinal.datastruct.Slot;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class SWAP extends NoOperandsInstruction {

    /**
     * 交换操作数栈栈顶的两个元素且元素必须都只占一个槽位
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        Slot val1 = operandStack.popSlot();
        Slot val2 = operandStack.popSlot();
        operandStack.pushSlot(val1.clone());
        operandStack.pushSlot(val2.clone());
    }

}
