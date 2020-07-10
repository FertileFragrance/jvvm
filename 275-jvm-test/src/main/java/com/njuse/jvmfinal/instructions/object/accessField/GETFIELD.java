package com.njuse.jvmfinal.instructions.object.accessField;

import com.njuse.jvmfinal.datastruct.NonArrayObject;
import com.njuse.jvmfinal.instructions.abstractIns.Index16Instruction;
import com.njuse.jvmfinal.memory.heap.InstanceVars;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.InitState;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.FieldRef;
import com.njuse.jvmfinal.memory.methodArea.MethodArea;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

public class GETFIELD extends Index16Instruction {

    /**
     * 访问对象的实例字段，流程是：
     * 1. 由读取好的index在运行时常量池中找到字段引用并解析成字段
     * 2. 向操作数栈压入一个实例变量的值，该实例变量由实例字段的索引确定
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        Constant fieldRef = MethodArea.getInstance().getClassToRuntimeConstantPool().
                get(topStackFrame.getMethod().getClazz()).getConstant(index);
        assert fieldRef instanceof FieldRef;
        Field field = ((FieldRef) fieldRef).resolveFieldRef();
        int slotID = field.getSlotID();
        OperandStack operandStack = topStackFrame.getOperandStack();
        NonArrayObject nonArrayObject = (NonArrayObject) operandStack.popObject();
        InstanceVars instanceVars = nonArrayObject.getInstanceVars();
        switch (field.getDescriptor().charAt(0)) {
            case 'B':
                operandStack.pushInt(instanceVars.getInt(slotID));
                break;
            case 'C':
                operandStack.pushInt(instanceVars.getInt(slotID));
                break;
            case 'D':
                operandStack.pushDouble(instanceVars.getDouble(slotID));
                break;
            case 'F':
                operandStack.pushFloat(instanceVars.getFloat(slotID));
                break;
            case 'I':
                operandStack.pushInt(instanceVars.getInt(slotID));
                break;
            case 'J':
                operandStack.pushLong(instanceVars.getLong(slotID));
                break;
            case 'S':
                operandStack.pushInt(instanceVars.getInt(slotID));
                break;
            case 'Z':
                operandStack.pushInt(instanceVars.getInt(slotID));
                break;
            default:
                break;
        }
    }

}
