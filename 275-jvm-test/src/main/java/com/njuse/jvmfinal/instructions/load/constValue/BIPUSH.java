package com.njuse.jvmfinal.instructions.load.constValue;

import com.njuse.jvmfinal.instructions.abstractIns.Instruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

import java.nio.ByteBuffer;

public class BIPUSH extends Instruction {

    private byte value; /**读取的byte类型数值*/

    @Override
    public void fetchOperands(ByteBuffer codeReader) {
        this.value = codeReader.get();
    }

    /**
     * 将byte带符号扩展为一个int类型并压入操作数栈中
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        operandStack.pushInt(this.value);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " value : " + this.value;
    }

}
