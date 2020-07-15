package com.njuse.jvmfinal.instructions.object;

import com.njuse.jvmfinal.datastruct.JObject;
import com.njuse.jvmfinal.datastruct.NullObject;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.memory.methodArea.MethodArea;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class INSTANCEOF extends Index16Instruction {

    /**
     * 检查某个对象是否是某个类的实例，主要过程是：
     * 1. 从操作数栈pop一个对象引用，若为空则向操作数栈压入0并结束此指令
     * 2. 由读取好的index在运行时常量池中找到类引用并解析成类
     * 3. 判断该引用是否
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        JObject objectRef = operandStack.popObject();
        if (objectRef == null || objectRef instanceof NullObject) {
            operandStack.pushInt(0);
            return;
        }
        Constant classRef = MethodArea.getInstance().getClassToRuntimeConstantPool().
                get(topStackFrame.getMethod().getClazz()).getConstant(index);
        assert classRef instanceof ClassRef;
        JClass jClass = ((ClassRef) classRef).resolveClassRef();
        if (jClass == null) {
            String message = "类引用是" + ((ClassRef) classRef).getClassName();
            throw new NullPointerException(message);
        }
        if (objectRef.isInstanceOf(jClass)) {
            operandStack.pushInt(1);
        } else {
            operandStack.pushInt(0);
        }
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() + "\n";
    }

}
