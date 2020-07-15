package com.njuse.jvmfinal.instructions.load.constValue;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class ICONST_N extends NoOperandsInstruction {

    private int value;  /**要被push的int类型数值*/

    public ICONST_N(int value) {
        this.value = value;
    }

    /**
     * 将一个int类型常量压入操作数栈中
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        //operandStack.pushInt(value);
        try {
            operandStack.pushInt(value);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException(Interpreter.message);
        }
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

    @Override
    public String toString() {
        String suffix = (value == -1) ? "M1" : "" + value;
        String simpleName = this.getClass().getSimpleName();
        return simpleName.substring(0, simpleName.length() - 1) + suffix;
    }

}
