package com.njuse.jvmfinal.instructions.operate;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.Index8Instruction;
import com.njuse.jvmfinal.memory.threadStack.LocalVariableTable;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

import java.nio.ByteBuffer;

public class IINC extends Index8Instruction {

    private int constValue; /**读取的常量，由byte类型符号扩展而来*/

    @Override
    public void fetchOperands(ByteBuffer codeReader) {
        super.fetchOperands(codeReader);
        this.constValue = codeReader.get();
    }

    /**
     * 常量带符号扩展成一个int类型数值，然后由index定位到的局部变量增加这个常量值
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        LocalVariableTable localVariableTable = topStackFrame.getLocalVariableTable();
        localVariableTable.setInt(index, constValue + localVariableTable.getInt(index));
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\n";
    }

}
