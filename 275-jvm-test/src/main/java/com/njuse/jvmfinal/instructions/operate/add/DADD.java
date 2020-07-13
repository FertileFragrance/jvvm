package com.njuse.jvmfinal.instructions.operate.add;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class DADD extends NoOperandsInstruction {

    /**
     * 从操作数栈中依次获取两个double类型的数，做加法后将结果push到操作数栈栈顶
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        double val2 = operandStack.popDouble();
        double val1 = operandStack.popDouble();
        double res = val1 + val2;
        operandStack.pushDouble(res);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

}
