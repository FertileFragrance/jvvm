package com.njuse.jvmfinal.instructions.operate.shift;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class ISHL extends NoOperandsInstruction {

    /**
     * 从操作数栈中依次获取两个int类型的数，将后取出的数左移先取出的数的低五位所表示的数，计算结果push到操作数栈栈顶
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        int val2 = operandStack.popInt();
        int val1 = operandStack.popInt();
        int res = val1 << (val2 & 0x1f);
        operandStack.pushInt(res);
    }

}
