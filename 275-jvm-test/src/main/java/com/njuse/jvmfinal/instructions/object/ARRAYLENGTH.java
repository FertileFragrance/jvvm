package com.njuse.jvmfinal.instructions.object;

import com.njuse.jvmfinal.datastruct.array.ArrayObject;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class ARRAYLENGTH extends NoOperandsInstruction {

    /**
     * 从操作数栈push一个数组对象，拿到其长度后放入操作数栈
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        ArrayObject arrayObject = (ArrayObject) operandStack.popObject();
        operandStack.pushInt(arrayObject.getLength());
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() + "\n";
    }

}
