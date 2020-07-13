package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.memory.threadStack.LocalVariableTable;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class ASTORE_N extends STORE_N {

    public ASTORE_N(int index) {
        this.index = index;
    }

    /**
     * 将操作数栈栈顶的引用类型对象保存到index所指的局部变量表
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        LocalVariableTable localVariableTable = topStackFrame.getLocalVariableTable();
        localVariableTable.setObject(index, operandStack.popObject());
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

}
