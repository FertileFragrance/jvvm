package com.njuse.jvmfinal.instructions.object.store;

import com.njuse.jvmfinal.datastruct.array.CharArrayObject;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.NoOperandsInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class CASTORE extends NoOperandsInstruction {

    /**
     * 从操作数栈先后pop值、索引和char数组对象，给索引位置赋值
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        int value = operandStack.popInt();
        int index = operandStack.popInt();
        CharArrayObject charArrayObject = (CharArrayObject) operandStack.popObject();
        charArrayObject.getArray()[index] = (char) value;
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

}
