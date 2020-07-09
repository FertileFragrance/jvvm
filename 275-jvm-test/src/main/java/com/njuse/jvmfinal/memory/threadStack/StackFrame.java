package com.njuse.jvmfinal.memory.threadStack;

import com.njuse.jvmfinal.memory.jclass.Method;
import lombok.Getter;

@Getter
/**
 * 此类表示栈帧对象，被线程栈组合
 */
public class StackFrame {

    private OperandStack operandStack;              /**栈帧内部的操作数栈*/
    private LocalVariableTable localVariableTable;  /**栈帧内部的局部变量表*/
    private ThreadStack threadStack;                /**持有栈帧所在的线程栈的引用*/
    private Method method;                          /**持有栈帧所执行的方法的引用*/
    private int nextPC = 0;

    public StackFrame(ThreadStack threadStack, Method method, int maxLocalVariableTableSize, int maxOperandStackSize) {
        this.threadStack = threadStack;
        this.method = method;
        this.localVariableTable = new LocalVariableTable(maxLocalVariableTableSize);
        this.operandStack = new OperandStack(maxOperandStackSize);
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }

}
