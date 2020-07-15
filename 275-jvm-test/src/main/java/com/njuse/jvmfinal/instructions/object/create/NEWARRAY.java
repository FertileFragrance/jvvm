package com.njuse.jvmfinal.instructions.object.create;

import com.njuse.jvmfinal.classloader.ClassLoader;
import com.njuse.jvmfinal.classloader.classfilereader.classpath.EntryType;
import com.njuse.jvmfinal.datastruct.array.ArrayObject;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.Instruction;
import com.njuse.jvmfinal.datastruct.array.ArrayType;
import com.njuse.jvmfinal.memory.heap.JHeap;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

import java.nio.ByteBuffer;

public class NEWARRAY extends Instruction {

    private int type;  /**创建的数组的大小*/

    /**
     * 获取一个字节的元素类型，它是一个int值
     * @param codeReader 读码器
     */
    @Override
    public void fetchOperands(ByteBuffer codeReader) {
        this.type = codeReader.get() & 0xff;
    }

    /**
     * 创建新的数组对象，主要过程是：
     * 1. 从操作数栈pop一个int类型的数作为数组的长度
     * 2. 根据读取好的type选择加载不同种类的数组类
     * 3. 创建新的对象添加到堆中，并向操作数栈栈顶push一个该对象的引用
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        int count = operandStack.popInt();
        ClassLoader classLoader = ClassLoader.getInstance();
        EntryType loadEntryType = topStackFrame.getMethod().getClazz().getLoadEntryType();
        JClass jClass = null;
        switch (this.type) {
            case ArrayType.T_BOOLEAN:
                jClass = classLoader.loadClass("[Z", null, loadEntryType);
                break;
            case ArrayType.T_CHAR:
                jClass = classLoader.loadClass("[C", null, loadEntryType);
                break;
            case ArrayType.T_FLOAT:
                jClass = classLoader.loadClass("[F", null, loadEntryType);
                break;
            case ArrayType.T_DOUBLE:
                jClass = classLoader.loadClass("[D", null, loadEntryType);
                break;
            case ArrayType.T_BYTE:
                jClass = classLoader.loadClass("[B", null, loadEntryType);
                break;
            case ArrayType.T_SHORT:
                jClass = classLoader.loadClass("[S", null, loadEntryType);
                break;
            case ArrayType.T_INT:
                jClass = classLoader.loadClass("[I", null, loadEntryType);
                break;
            case ArrayType.T_LONG:
                jClass = classLoader.loadClass("[J", null, loadEntryType);
                break;
            default:
                break;
        }
        ArrayObject arrayObject = jClass.newArrayObject(count);
        JHeap.getInstance().addObject(arrayObject);
        operandStack.pushObject(arrayObject);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " array type : " + ArrayType.getName(this.type);
    }

}
