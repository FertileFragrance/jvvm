package com.njuse.jvmfinal.instructions.load.constValue;

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
     * TODO 如果运行时常量池对应的索引项是一个字符串字面量的引用，那这个实例的引用所对应的reference类型数据value将被压入到操作数栈之中
     * TODO 如果运行时常量池对应的索引项是一个类的符号引用，这个符号引用是已被解析过的，那这个类的Class对象所对应的reference类型数据value将被压入到操作数栈之中
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        Constant constant = topStackFrame.getMethod().getClazz().getRuntimeConstantPool().getConstant(index);
        constant = MethodArea.getInstance().getClassToRuntimeConstantPool().
                get(topStackFrame.getMethod().getClazz()).getConstant(index);
        if (constant instanceof IntWrapper) {
            operandStack.pushInt(((IntWrapper) constant).getValue());
        } else if (constant instanceof FloatWrapper) {
            operandStack.pushFloat(((FloatWrapper) constant).getValue());
        } else if (constant instanceof StringWrapper) {
            //operandStack.pushObject(((StringWrapper) constant).getValue());
        } else if (constant instanceof ClassRef) {

        }
    }

}
