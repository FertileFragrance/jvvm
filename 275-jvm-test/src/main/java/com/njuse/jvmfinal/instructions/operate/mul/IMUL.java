package com.njuse.jvmfinal.instructions.operate.mul;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class IMUL extends NoOperandsInstruction {

    /**
     * 从操作数栈中依次获取两个int类型的数，做乘法后将结果push到操作数栈栈顶
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        int val2 = operandStack.popInt();
        int val1 = operandStack.popInt();
        int res = val1 * val2;
        operandStack.pushInt(res);
    }

}
