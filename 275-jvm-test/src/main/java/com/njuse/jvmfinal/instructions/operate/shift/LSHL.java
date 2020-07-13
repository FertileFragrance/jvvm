package com.njuse.jvmfinal.instructions.operate.shift;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class LSHL extends NoOperandsInstruction {

    /**
     * 从操作数栈中依次获取两个long类型的数，将后取出的数左移先取出的数的低六位所表示的数，计算结果push到操作数栈栈顶
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        long val2 = operandStack.popInt();
        long val1 = operandStack.popInt();
        long res = val1 << (val2 & 0x3f);
        operandStack.pushLong(res);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\n";
    }

}
