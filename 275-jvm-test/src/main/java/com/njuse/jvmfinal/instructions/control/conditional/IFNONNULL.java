package com.njuse.jvmfinal.instructions.control.conditional;

import com.njuse.jvmfinal.datastruct.JObject;
import com.njuse.jvmfinal.datastruct.NullObject;
import com.njuse.jvmfinal.instructions.abstractIns.BranchInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class IFNONNULL extends BranchInstruction {

    /**
     * 从操作数栈获得一个引用类型数据，若不为空则重新设置PC值
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        JObject val = operandStack.popObject();
        if (!(val instanceof NullObject)) {
            int branchPC = topStackFrame.getNextPC() - 3 + this.offset;
            topStackFrame.setNextPC(branchPC);
        }
    }

}
