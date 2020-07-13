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

public class PUTSTATIC extends Index16Instruction {

    /**
     * 设置类的静态字段，流程是：
     * 1. 由读取好的index在运行时常量池中找到字段引用并解析成字段
     * 2. 如果字段所在的类没有进行过初始化，则会触发其初始化过程
     * 3. 给静态变量的相应的索引位置设置一个值，该值是从操作数栈栈顶pop出来的
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
                staticVars.setInt(slotID, operandStack.popInt());
                break;
            case 'C':
                staticVars.setInt(slotID, operandStack.popInt());
                break;
            case 'D':
                staticVars.setDouble(slotID, operandStack.popDouble());
                break;
            case 'F':
                staticVars.setFloat(slotID, operandStack.popFloat());
                break;
            case 'I':
                staticVars.setInt(slotID, operandStack.popInt());
                break;
            case 'J':
                staticVars.setLong(slotID, operandStack.popLong());
                break;
            case 'S':
                staticVars.setInt(slotID, operandStack.popInt());
                break;
            case 'Z':
                staticVars.setInt(slotID, operandStack.popInt());
                break;
            case 'L':
                staticVars.setObject(slotID, operandStack.popObject());
                break;
            case '[':
                staticVars.setObject(slotID, operandStack.popObject());
                break;
            default:
                break;
        }
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + "字段所在的类是" + field.getClazz().getName() + "\t" +
                "字段是" + field.getName() + "\n";
    }

}
