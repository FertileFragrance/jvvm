package com.njuse.jvmfinal.instructions.operate.compare;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class FCMPL extends NoOperandsInstruction {

    /**
     * 从操作数栈中依次获取两个float类型的数,进行大小比较
     * 如果两个数至少有一个为NaN，则将整数-1push到操作数栈栈顶
     * 如果val1 > val2，则将整数1push到操作数栈栈顶
     * 如果val1 == val2，则将整数0push到操作数栈栈顶
     * 如果val1 < val2，则将整数-1push到操作数栈栈顶
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        float val2 = operandStack.popFloat();
        float val1 = operandStack.popFloat();
        if (val1 == Float.NaN || val2 == Float.NaN) {
            operandStack.pushInt(-1);
            return;
        }
        if (val1 > val2) {
            operandStack.pushInt(1);
        } else if (val1 == val2) {
            operandStack.pushInt(0);
        } else {
            operandStack.pushInt(-1);
        }
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

}
