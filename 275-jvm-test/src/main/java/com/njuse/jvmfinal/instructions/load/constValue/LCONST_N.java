package com.njuse.jvmfinal.instructions.load.constValue;

import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class LCONST_N extends NoOperandsInstruction {

    private long value; /**要被push的long类型数值*/

    public LCONST_N(long value) {
        this.value = value;
    }

    /**
     * 将一个long类型常量压入操作数栈中
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        operandStack.pushLong(value);
    }

    @Override
    public String toString() {
        String simpleName = this.getClass().getSimpleName();
        return simpleName.substring(0, simpleName.length() - 1) + this.value;
    }

}
