package com.njuse.jvmfinal.instructions.control.conditional;

import com.njuse.jvmfinal.datastruct.JObject;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.BranchInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class IF_ACMPEQ extends BranchInstruction {

    /**
     * 从操作数栈获得两个引用类型数据，若相同则重新设置PC值
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        JObject val2 = operandStack.popObject();
        JObject val1 = operandStack.popObject();
        if (val1 == val2) {
            int branchPC = topStackFrame.getNextPC() - 3 + this.offset;
            topStackFrame.setNextPC(branchPC);
        }
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\n";
    }

}
