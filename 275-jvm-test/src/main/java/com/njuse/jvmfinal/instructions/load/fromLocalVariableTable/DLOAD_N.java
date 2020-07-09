package com.njuse.jvmfinal.instructions.load.fromLocalVariableTable;

import com.njuse.jvmfinal.memory.threadStack.LocalVariableTable;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class DLOAD_N extends LOAD_N {

    public DLOAD_N(int index) {
        this.index = index;
    }

    /**
     * 将index所指的局部变量表的double类型数值压入到操作数栈栈顶
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        LocalVariableTable localVariableTable = topStackFrame.getLocalVariableTable();
        operandStack.pushDouble(localVariableTable.getDouble(index));
    }

}
