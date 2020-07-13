package com.njuse.jvmfinal.instructions.operate.add;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class FADD extends NoOperandsInstruction {

    /**
     * 从操作数栈中依次获取两个float类型的数，做加法后将结果push到操作数栈栈顶
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        float val2 = operandStack.popFloat();
        float val1 = operandStack.popFloat();
        float res = val1 + val2;
        operandStack.pushFloat(res);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\n";
    }

}
