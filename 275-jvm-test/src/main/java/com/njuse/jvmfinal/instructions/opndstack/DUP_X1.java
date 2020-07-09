package com.njuse.jvmfinal.instructions.opndstack;

import com.njuse.jvmfinal.datastruct.Slot;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class DUP_X1 extends NoOperandsInstruction {

    /**
     * 复制操作数栈栈顶的一个元素且该元素的类型必须只占一个槽位，再压入操作数栈栈顶以下两个槽位之后
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        Slot val1 = operandStack.popSlot();
        Slot val2 = operandStack.popSlot();
        operandStack.pushSlot(val1);
        operandStack.pushSlot(val2);
        operandStack.pushSlot(val1);
    }

}
