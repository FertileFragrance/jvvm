package com.njuse.jvmfinal.instructions.ret;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;
import com.njuse.jvmfinal.memory.threadStack.ThreadStack;

public class LRETURN extends NoOperandsInstruction {

    /**
     * 从顶层栈帧的操作数栈中pop一个long类型的值作为返回值，这个栈帧弹出后将此值压入新的顶层栈帧的操作数栈的栈顶
     * @param topStackFrame 原来的顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        ThreadStack threadStack = topStackFrame.getThreadStack();
        OperandStack operandStack = topStackFrame.getOperandStack();
        threadStack.popStackFrame();
        threadStack.getTopStackFrame().getOperandStack().pushLong(operandStack.popLong());
        if (!(threadStack.getTopStackFrame() == null)) {
            Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                    topStackFrame.getMethod().getName() + "\t" + "返回的方法所在的类是"  + threadStack.getTopStackFrame()
                    .getMethod().getClazz().getName() + "\t" + "返回的方法是" + threadStack.getTopStackFrame().getMethod().getName() + "\n";
        } else {
            Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                    topStackFrame.getMethod().getName() + "\n";
        }
    }

}
