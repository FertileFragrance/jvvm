package com.njuse.jvmfinal.instructions.object.accessField;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.FieldRef;
import com.njuse.jvmfinal.memory.methodArea.MethodArea;
import com.njuse.jvmfinal.memory.methodArea.StaticVars;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class GETSTATIC extends Index16Instruction {

    /**
     * 访问类的静态字段，流程是：
     * 1. 由读取好的index在运行时常量池中找到字段引用并解析成字段
     * 2. 如果字段所在的类没有进行过初始化，则会触发其初始化过程
     * 3. 向操作数栈压入一个静态变量的值，该静态变量由静态字段的索引确定
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        Constant fieldRef = MethodArea.getInstance().getClassToRuntimeConstantPool().
                get(topStackFrame.getMethod().getClazz()).getConstant(index);
        assert fieldRef instanceof FieldRef;
        Field field = ((FieldRef) fieldRef).resolveFieldRef();
        if (field.getClazz().getInitState() == InitState.PREPARED) {
            topStackFrame.setNextPC(topStackFrame.getNextPC() - 3);
            field.getClazz().initClass(topStackFrame.getThreadStack(), field.getClazz());
            return;
        }
        int slotID = field.getSlotID();
        StaticVars staticVars = field.getClazz().getStaticVars();
        OperandStack operandStack = topStackFrame.getOperandStack();
        switch (field.getDescriptor().charAt(0)) {
            case 'B':
                operandStack.pushInt(staticVars.getInt(slotID));
                break;
            case 'C':
                operandStack.pushInt(staticVars.getInt(slotID));
                break;
            case 'D':
                operandStack.pushDouble(staticVars.getDouble(slotID));
                break;
            case 'F':
                operandStack.pushFloat(staticVars.getFloat(slotID));
                break;
            case 'I':
                operandStack.pushInt(staticVars.getInt(slotID));
                break;
            case 'J':
                operandStack.pushLong(staticVars.getLong(slotID));
                break;
            case 'S':
                operandStack.pushInt(staticVars.getInt(slotID));
                break;
            case 'Z':
                operandStack.pushInt(staticVars.getInt(slotID));
                break;
            case 'L':
                operandStack.pushObject(staticVars.getObject(slotID));
                break;
            case '[':
                operandStack.pushObject(staticVars.getObject(slotID));
                break;
            default:
                break;
        }
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() + "\t" + "字段所在的类是" +
                field.getClazz().getName() + "\t" + "字段是" + field.getName() + "\n";
    }

}
