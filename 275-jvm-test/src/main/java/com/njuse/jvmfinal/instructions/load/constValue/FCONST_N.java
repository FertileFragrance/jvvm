package com.njuse.jvmfinal.instructions.load.constValue;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class FCONST_N extends NoOperandsInstruction {

    private float value;    /**要被push的float类型数值*/

    public FCONST_N(float value) {
        this.value = value;
    }

    /**
     * 将一个float类型常量压入操作数栈中
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        operandStack.pushFloat(value);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

    @Override
    public String toString() {
        String simpleName = this.getClass().getSimpleName();
        return simpleName.substring(0, simpleName.length() - 1) + this.value;
    }

}
