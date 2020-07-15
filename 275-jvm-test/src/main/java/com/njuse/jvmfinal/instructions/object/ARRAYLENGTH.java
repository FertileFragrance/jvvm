package com.njuse.jvmfinal.instructions.object;

import com.njuse.jvmfinal.datastruct.JObject;
import com.njuse.jvmfinal.datastruct.NonArrayObject;
import com.njuse.jvmfinal.datastruct.NullObject;
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
        ArrayObject arrayObject = null;
        JObject jObject = null;
        try {
            jObject = operandStack.popObject();
            ArrayObject a = (ArrayObject) jObject;
            operandStack.pushInt(a.getLength());
        } catch (ClassCastException e) {
            String message = "object是null吗？" + (jObject == null) + "\n";
            message += "object是空对象类型吗？" + (jObject instanceof NullObject) + "\n";
            message += "object是非数组对象类型吗？" + (jObject instanceof NonArrayObject) + "\n";
            message += "object是数组对象类型吗？" + (jObject instanceof ArrayObject) + "\n";
            throw new ClassCastException(message + Interpreter.message);
        }
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() + "\n";
    }

}
