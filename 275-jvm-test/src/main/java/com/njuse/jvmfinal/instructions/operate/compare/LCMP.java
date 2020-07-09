package com.njuse.jvmfinal.instructions.operate.compare;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class LCMP extends NoOperandsInstruction {

    /**
     * 从操作数栈中依次获取两个long类型的数,进行大小比较
     * 如果val1 > val2，则将整数1push到操作数栈栈顶
     * 如果val1 == val2，则将整数0push到操作数栈栈顶
     * 如果val1 < val2，则将整数-1push到操作数栈栈顶
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        long val2 = operandStack.popLong();
        long val1 = operandStack.popLong();
        if (val1 > val2) {
            operandStack.pushInt(1);
        } else if (val1 == val2) {
            operandStack.pushInt(0);
        } else {
            operandStack.pushInt(-1);
        }
    }

}
