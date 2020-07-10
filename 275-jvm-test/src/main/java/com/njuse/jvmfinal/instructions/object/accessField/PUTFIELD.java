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

public class PUTFIELD extends Index16Instruction {

    /**
     * 设置对象的实例字段，流程是：
     * 1. 由读取好的index在运行时常量池中找到字段引用并解析成字段
     * 2. 先后从操作数栈中取出值和对象，给对象相应槽位的实例变量赋值
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
        NonArrayObject nonArrayObject;
        switch (field.getDescriptor().charAt(0)) {
            case 'B':
                int byteVal = operandStack.popInt();
                nonArrayObject = (NonArrayObject) operandStack.popObject();
                nonArrayObject.getInstanceVars().setInt(slotID, byteVal);
                break;
            case 'C':
                int charVal = operandStack.popInt();
                nonArrayObject = (NonArrayObject) operandStack.popObject();
                nonArrayObject.getInstanceVars().setInt(slotID, charVal);
                break;
            case 'D':
                double doubleVal = operandStack.popDouble();
                nonArrayObject = (NonArrayObject) operandStack.popObject();
                nonArrayObject.getInstanceVars().setDouble(slotID, doubleVal);
                break;
            case 'F':
                float floatVal = operandStack.popFloat();
                nonArrayObject = (NonArrayObject) operandStack.popObject();
                nonArrayObject.getInstanceVars().setFloat(slotID, floatVal);
                break;
            case 'I':
                int intVal = operandStack.popInt();
                nonArrayObject = (NonArrayObject) operandStack.popObject();
                nonArrayObject.getInstanceVars().setInt(slotID, intVal);
                break;
            case 'J':
                long longVal = operandStack.popLong();
                nonArrayObject = (NonArrayObject) operandStack.popObject();
                nonArrayObject.getInstanceVars().setLong(slotID, longVal);
                break;
            case 'S':
                int shortVal = operandStack.popInt();
                nonArrayObject = (NonArrayObject) operandStack.popObject();
                nonArrayObject.getInstanceVars().setInt(slotID, shortVal);
                break;
            case 'Z':
                int booleanVal = operandStack.popInt();
                nonArrayObject = (NonArrayObject) operandStack.popObject();
                nonArrayObject.getInstanceVars().setInt(slotID, booleanVal);
                break;
            default:
                break;
        }
    }

}
