package com.njuse.jvmfinal.instructions.load.constValue;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.Instruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

import java.nio.ByteBuffer;

public class SIPUSH extends Instruction {

    private int value;  /**读取的两个字节组成的值*/

    @Override
    public void fetchOperands(ByteBuffer codeReader) {
        this.value = codeReader.getShort();
    }

    /**
     * byte1和byte2通过(byte1 << 8) | byte2方式构造成一个short类型数值，相当于读了一个short类型的数
     * 将此数值带符号扩展为一个int类型的值value，然后将value压入到操作数栈中
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        operandStack.pushInt(this.value);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " value : " + this.value;
    }

}
