package com.njuse.jvmfinal.instructions.load.constValue;

import com.njuse.jvmfinal.execution.Interpreter;
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
        //operandStack.pushLong(value);
        try {
            operandStack.pushLong(value);
        } catch (ArrayIndexOutOfBoundsException e) {
            String message = "当前执行的方法是" + topStackFrame.getMethod().getName() + "\n";
            message += "当前方法操作数栈最大容量是" + topStackFrame.getMethod().getMaxStack() + "\n";
            message += "当前执行的方法所在的类是" + topStackFrame.getMethod().getClazz().getName() + "\n";
            message += "操作数栈信息" + topStackFrame.getOperandStack().toString() + "\n\n\n";
            message += Interpreter.message;
            throw new ArrayIndexOutOfBoundsException(message);
        }

        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\n";
    }

    @Override
    public String toString() {
        String simpleName = this.getClass().getSimpleName();
        return simpleName.substring(0, simpleName.length() - 1) + this.value;
    }

}
