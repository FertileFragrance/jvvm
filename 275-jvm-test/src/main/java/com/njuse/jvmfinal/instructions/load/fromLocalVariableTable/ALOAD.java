package com.njuse.jvmfinal.instructions.load.fromLocalVariableTable;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.Index8Instruction;
import com.njuse.jvmfinal.memory.threadStack.LocalVariableTable;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class ALOAD extends Index8Instruction {

    /**
     * 将index所指的局部变量表的引用类型对象压入到操作数栈栈顶
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        LocalVariableTable localVariableTable = topStackFrame.getLocalVariableTable();
        operandStack.pushObject(localVariableTable.getObject(index));
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

}
