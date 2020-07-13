package com.njuse.jvmfinal.instructions.object;

import com.njuse.jvmfinal.datastruct.NonArrayObject;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.Index16Instruction;
import com.njuse.jvmfinal.memory.heap.JHeap;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.memory.methodArea.MethodArea;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class NEW extends Index16Instruction {

    /**
     * 创建新的非数组对象，主要过程是：
     * 1. 由读取好的index在运行时常量池中找到类引用并解析成类
     * 2. 如果解析得到的类没有进行过初始化，则会触发其初始化过程
     * 3. 创建新的对象添加到堆中，并向操作数栈栈顶push一个该对象的引用
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        Constant classRef = MethodArea.getInstance().getClassToRuntimeConstantPool().
                get(topStackFrame.getMethod().getClazz()).getConstant(index);
        assert classRef instanceof ClassRef;
        JClass jClass = ((ClassRef) classRef).resolveClassRef();;
        if (jClass.getInitState() == InitState.PREPARED) {
            topStackFrame.setNextPC(topStackFrame.getNextPC() - 3);
            jClass.initClass(topStackFrame.getThreadStack(), jClass);
            return;
        }
        NonArrayObject nonArrayObject = jClass.newObject();
        JHeap.getInstance().addObject(nonArrayObject);
        topStackFrame.getOperandStack().pushObject(nonArrayObject);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getOperandStack() + "\t" + topStackFrame.getMethod().getName() + "\t" +
                "创建的对象实例所在的类是" + jClass.getName() + "\n";
    }

}
