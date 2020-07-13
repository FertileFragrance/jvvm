package com.njuse.jvmfinal.instructions.load.constValue;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class DCONST_N extends NoOperandsInstruction {

    private double value;   /**要被push的int类型数值*/

    public DCONST_N(double value) {
        this.value = value;
    }

    /**
     * 将一个double类型常量压入操作数栈中
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        operandStack.pushDouble(value);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\n";
    }

    @Override
    public String toString() {
        String simpleName = this.getClass().getSimpleName();
        return simpleName.substring(0, simpleName.length() - 1) + this.value;
    }

}
