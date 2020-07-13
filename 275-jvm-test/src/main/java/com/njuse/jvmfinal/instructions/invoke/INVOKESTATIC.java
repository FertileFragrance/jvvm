package com.njuse.jvmfinal.instructions.invoke;

import com.njuse.jvmfinal.datastruct.Slot;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.MethodRef;
import com.njuse.jvmfinal.memory.methodArea.MethodArea;
import com.njuse.jvmfinal.memory.threadStack.LocalVariableTable;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class INVOKESTATIC extends Index16Instruction {

    /**
     * 调用静态方法的指令，做了以下几件事：
     * 1. 由已经读取好的index到调用者所在的类的运行时常量池中找到方法引用
     * 2. 由方法引用解析方法找到要调用的静态方法
     * 3. 如果方法所在的类没有进行过初始化，则会触发其初始化过程
     * 4. 从当前方法的操作数栈中pop要调用的静态方法所占的槽的个数这么多个槽位
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
        if (method.getClazz().getInitState() == InitState.PREPARED) {
            topStackFrame.setNextPC(topStackFrame.getNextPC() - 3);
            method.getClazz().initClass(topStackFrame.getThreadStack(), method.getClazz());
            return;
        }

        /**
         * 非标准部分
         */
        if (method.getClazz().getName().equals("cases/TestUtil")) {
            if (method.getName().equals("reach")) {
                int arg = topStackFrame.getOperandStack().popInt();
                topStackFrame.getOperandStack().pushInt(arg);
                System.out.println(arg);
            } else if (method.getName().equals("equalInt")) {
                int arg2 = topStackFrame.getOperandStack().popInt();
                int arg1 = topStackFrame.getOperandStack().popInt();
                topStackFrame.getOperandStack().pushInt(arg1);
                topStackFrame.getOperandStack().pushInt(arg2);
                if (arg1 != arg2) {
                    throw new RuntimeException(arg1 + "!=" + arg2);
                }
            } else if (method.getName().equals("equalFloat")) {
                float arg2 = topStackFrame.getOperandStack().popFloat();
                float arg1 = topStackFrame.getOperandStack().popFloat();
                topStackFrame.getOperandStack().pushFloat(arg1);
                topStackFrame.getOperandStack().pushFloat(arg2);
                if (arg1 != arg2) {
                    throw new RuntimeException(arg1 + "!=" + arg2);
                }
            }
        }

        int argc = method.getArgc();
        Slot[] argv = new Slot[argc];
        for (int i = 0; i < argc; i++) {
            argv[i] = topStackFrame.getOperandStack().popSlot();
        }
        StackFrame newFrame = prepareNewFrame(topStackFrame, argc, argv, method);
        topStackFrame.getThreadStack().pushStackFrame(newFrame);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + "调用的静态方法所在的类是"  + method.getClazz().getName() +
                "\t" + "调用的静态方法是" + method.getName() + "\n";
    }

    private StackFrame prepareNewFrame(StackFrame stackFrame, int argc, Slot[] argv, Method method) {
        StackFrame newStackFrame = new StackFrame(stackFrame.getThreadStack(), method,
                method.getMaxLocal() + 1, method.getMaxStack());
        LocalVariableTable localVariableTable = newStackFrame.getLocalVariableTable();
        for (int i = 0; i < argc; i++) {
            localVariableTable.setSlot(i, argv[argc-1-i]);
        }
        return newStackFrame;
    }

}
