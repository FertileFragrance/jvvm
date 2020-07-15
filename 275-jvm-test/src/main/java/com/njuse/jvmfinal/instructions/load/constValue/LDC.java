package com.njuse.jvmfinal.instructions.load.constValue;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.Index8Instruction;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.FloatWrapper;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.IntWrapper;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.wrapper.StringWrapper;
import com.njuse.jvmfinal.memory.methodArea.MethodArea;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class LDC extends Index8Instruction {

    /**
     * 如果运行时常量池对应的索引项是一个int或者float类型数据，该数据的值将被压入到操作数栈中
     * 如果运行时常量池对应的索引项是一个字符串字面量的引用，该引用所对应的值将被压入到操作数栈中（不考虑）
     * 如果运行时常量池对应的索引项是一个类的符号引用，该类的对象所对应的引用类型数据将被压入到操作数栈中（不考虑）
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        Constant constant = MethodArea.getInstance().getClassToRuntimeConstantPool().
                get(topStackFrame.getMethod().getClazz()).getConstant(index);
        if (constant instanceof IntWrapper) {
            operandStack.pushInt(((IntWrapper) constant).getValue());
        } else if (constant instanceof FloatWrapper) {
            operandStack.pushFloat(((FloatWrapper) constant).getValue());
        } else if (constant instanceof StringWrapper) {

        } else if (constant instanceof ClassRef) {

        }
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

}
