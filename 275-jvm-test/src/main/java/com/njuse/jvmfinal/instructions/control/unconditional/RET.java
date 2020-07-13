package com.njuse.jvmfinal.instructions.control.unconditional;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.Index8Instruction;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class RET extends Index8Instruction {

    /**
     * index所指的局部变量表的位置应是一个returnAddress类型的局部变量，将此值更新到PC中
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        // TODO
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\n";
    }

}
