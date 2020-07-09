package com.njuse.jvmfinal.instructions.invoke;

import com.njuse.jvmfinal.datastruct.JObject;
import com.njuse.jvmfinal.datastruct.Slot;
import com.njuse.jvmfinal.instructions.abstractIns.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.MethodRef;
import com.njuse.jvmfinal.memory.methodArea.MethodArea;
import com.njuse.jvmfinal.memory.threadStack.LocalVariableTable;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class INVOKEVIRTUAL extends Index16Instruction {

    /**
     * 调用实例方法的指令，做了以下几件事：
     * 1. 由已经读取好的index到调用者所在的类的运行时常量池中找到方法引用
     * 2. 由方法引用解析方法找到要调用的（可能是其父类的）实例方法
     * 3. 从当前方法的操作数栈中pop它所占的槽的个数这么多个槽位，最后pop代表实例对象的槽
     * 4. 由实例对象所在的类找到要真正被调用的（父类的子类的）方法
     * 5. 新建一个栈帧并把从原来操作数栈中pop出来的数据存到新栈帧的局部变量表
     * 6. 把新栈帧push到线程栈
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        Constant methodRef = MethodArea.getInstance().getClassToRuntimeConstantPool().
                get(topStackFrame.getMethod().getClazz()).getConstant(index);
        assert methodRef instanceof MethodRef;
        Method method = ((MethodRef) methodRef).resolveMethodRef();
        if (method.isNative()) {
            return;
        }
        int argc = method.getArgc();
        Slot[] argv = new Slot[argc];
        for (int i = 0; i < argc; i++) {
            argv[i] = topStackFrame.getOperandStack().popSlot();
        }
        JObject objectRef = topStackFrame.getOperandStack().popObject();
        Method toInvoke = ((MethodRef) methodRef).resolveMethodRef(objectRef.getClazz());
        StackFrame newFrame = prepareNewFrame(topStackFrame, argc, argv, objectRef, toInvoke);
        topStackFrame.getThreadStack().pushStackFrame(newFrame);
    }

    private StackFrame prepareNewFrame(
            StackFrame stackFrame, int argc, Slot[] argv, JObject objectRef, Method toInvoke) {
        StackFrame newStackFrame = new StackFrame(stackFrame.getThreadStack(), toInvoke,
                toInvoke.getMaxLocal() + 1, toInvoke.getMaxStack());
        LocalVariableTable localVariableTable = newStackFrame.getLocalVariableTable();
        Slot thisSlot = new Slot();
        thisSlot.setObject(objectRef);
        localVariableTable.setSlot(0, thisSlot);
        for (int i = 1; i < argc + 1; i++) {
            localVariableTable.setSlot(i, argv[argc-i]);
        }
        return newStackFrame;
    }

}
