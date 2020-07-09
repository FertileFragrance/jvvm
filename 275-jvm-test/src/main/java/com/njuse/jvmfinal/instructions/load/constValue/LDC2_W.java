package com.njuse.jvmfinal.instructions.load.constValue;

import com.njuse.jvmfinal.instructions.abstractIns.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.*;
import com.njuse.jvmfinal.memory.methodArea.MethodArea;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class LDC2_W extends Index16Instruction {

    /**
     * 如果运行时常量池对应的索引项是一个int或者float类型数据，该数据的值将被压入到操作数栈中
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        Constant constant = MethodArea.getInstance().getClassToRuntimeConstantPool().
                get(topStackFrame.getMethod().getClazz()).getConstant(index);
        if (constant instanceof LongWrapper) {
            operandStack.pushLong(((LongWrapper) constant).getValue());
        } else if (constant instanceof DoubleWrapper) {
            operandStack.pushDouble(((DoubleWrapper) constant).getValue());
        }
    }

}
