package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.instructions.abstractIns.Index8Instruction;
import com.njuse.jvmfinal.memory.threadStack.LocalVariableTable;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class DSTORE extends Index8Instruction {

    /**
     * 将操作数栈栈顶的long类型数据保存到index所指的局部变量表
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        LocalVariableTable localVariableTable = topStackFrame.getLocalVariableTable();
        localVariableTable.setDouble(index, operandStack.popDouble());
    }

}
