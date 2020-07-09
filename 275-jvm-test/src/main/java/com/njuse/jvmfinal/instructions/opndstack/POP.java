package com.njuse.jvmfinal.instructions.opndstack;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class POP extends NoOperandsInstruction {

    /**
     * 将操作数栈栈顶的一个元素出栈且该元素的类型必须只占一个槽位
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        operandStack.popSlot();
    }

}
