package com.njuse.jvmfinal.instructions.operate.rem;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class IREM extends NoOperandsInstruction {

    /**
     * 从操作数栈中依次获取两个int类型的数，求余后将结果push到操作数栈栈顶，暂时忽略异常
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        int val2 = operandStack.popInt();
        int val1 = operandStack.popInt();
        assert val2 != 0;
        int res = val1 % val2;
        operandStack.pushInt(res);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

}
