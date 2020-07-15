package com.njuse.jvmfinal.instructions.object.create;

import com.njuse.jvmfinal.datastruct.array.ArrayObject;
import com.njuse.jvmfinal.datastruct.array.RefArrayObject;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.Index16Instruction;
import com.njuse.jvmfinal.memory.heap.JHeap;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.memory.methodArea.MethodArea;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

import java.nio.ByteBuffer;

public class MULTIANEWARRAY extends Index16Instruction {

    private int dimensions;

    /**
     * 此指令在读取index的基础上还要读一个字节，表示创建数组的维度
     * @param codeReader 读码器
     */
    @Override
    public void fetchOperands(ByteBuffer codeReader) {
        super.fetchOperands(codeReader);
        this.dimensions = codeReader.get() & 0xff;
    }

    /**
     * 创建新的多维数组对象，主要过程是：
     * 1. 从操作数栈pop维度个数的int类型数据作为数组的长度
     * 2. 由读取好的index在运行时常量池中找到类引用并解析成类
     * 3. 数组第一维的元素被初始化为第二维的子数组，后面每一维都依此类推
     * 4. 创建新的对象添加到堆中，并向操作数栈栈顶push一个该对象的引用
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        int[] counts = new int[dimensions];
        for (int i = dimensions - 1; i >= 0; i--) {
            counts[i] = operandStack.popInt();
        }
        Constant classRef = MethodArea.getInstance().getClassToRuntimeConstantPool().
                get(topStackFrame.getMethod().getClazz()).getConstant(index);
        assert classRef instanceof ClassRef;
        JClass jClass = ((ClassRef) classRef).resolveClassRef();
        ArrayObject arrayObject = createMultiArray(0, counts, jClass);
        JHeap.getInstance().addObject(arrayObject);
        operandStack.pushObject(arrayObject);
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\t" + operandStack.toString() +"\n";
    }

    private ArrayObject createMultiArray(int index, int[] counts, JClass jClass) {
        int length = counts[index];
        index++;
        ArrayObject arrayObject = jClass.newArrayObject(length);
        if (index < counts.length) {
            for (int i = 0;i < length; i++)
            ((RefArrayObject) arrayObject).getArray()[i] = createMultiArray(index, counts, jClass.getComponentClass());
        }
        return arrayObject;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " index : " + this.index + "dimension : " + this.dimensions;
    }

}
