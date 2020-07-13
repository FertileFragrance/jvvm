package com.njuse.jvmfinal.instructions.ret;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;
import com.njuse.jvmfinal.memory.threadStack.ThreadStack;

public class RETURN extends NoOperandsInstruction {

    /**
     * 将顶层栈帧弹出线程栈，不返回任何值
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        ThreadStack threadStack = topStackFrame.getThreadStack();
        threadStack.popStackFrame();
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + "返回的方法所在的类是"  + threadStack.getTopStackFrame()
                .getMethod().getClazz().getName() + "\t" + "返回的方法是" + threadStack.getTopStackFrame().getMethod() + "\n";
    }

}
