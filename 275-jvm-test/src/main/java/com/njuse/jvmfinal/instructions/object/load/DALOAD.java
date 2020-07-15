package com.njuse.jvmfinal.instructions.object.load;

import com.njuse.jvmfinal.datastruct.JObject;
import com.njuse.jvmfinal.datastruct.array.DoubleArrayObject;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class DALOAD extends NoOperandsInstruction {

    /**
     * 从操作数栈先后pop数组索引和double数组对象，通过索引找到值压入操作数栈
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        int index = operandStack.popInt();
        JObject doubleArrayObject = operandStack.popObject();
        operandStack.pushDouble(((DoubleArrayObject) doubleArrayObject).getArray()[index]);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

}
