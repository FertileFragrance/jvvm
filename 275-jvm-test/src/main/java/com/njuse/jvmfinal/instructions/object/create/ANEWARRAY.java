package com.njuse.jvmfinal.instructions.object.create;

import com.njuse.jvmfinal.datastruct.array.ArrayObject;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.Index16Instruction;
import com.njuse.jvmfinal.memory.heap.JHeap;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.memory.methodArea.MethodArea;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class ANEWARRAY extends Index16Instruction {

    /**
     * 创建新的引用类型数组对象，主要过程是：
     * 1. 从操作数栈pop一个int类型的数作为数组的长度
     * 2. 由读取好的index在运行时常量池中找到类引用并解析成组件类
     * 3. 通过类加载由组件类得到要创建的数组类
     * 4. 创建新的对象添加到堆中，并向操作数栈栈顶push一个该对象的引用
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        int count = operandStack.popInt();
        Constant classRef = MethodArea.getInstance().getClassToRuntimeConstantPool().
                get(topStackFrame.getMethod().getClazz()).getConstant(index);
        assert classRef instanceof ClassRef;
        JClass componentClass = ((ClassRef) classRef).resolveClassRef();
        JClass jClass = componentClass.getArrayClass();
        ArrayObject arrayObject = jClass.newArrayObject(count);
        JHeap.getInstance().addObject(arrayObject);
        operandStack.pushObject(arrayObject);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

}
