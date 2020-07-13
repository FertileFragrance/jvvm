package com.njuse.jvmfinal.instructions.opndstack;

import com.njuse.jvmfinal.datastruct.Slot;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class DUP extends NoOperandsInstruction {

    /**
     * 复制操作数栈栈顶的一个元素且该元素的类型必须只占一个槽位，再压入操作数栈栈顶
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        Slot val = operandStack.popSlot();
        operandStack.pushSlot(val.clone());
        operandStack.pushSlot(val.clone());
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\n";
    }

}
