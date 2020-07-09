package com.njuse.jvmfinal.instructions.abstractIns;

import com.njuse.jvmfinal.memory.threadStack.StackFrame;

import java.nio.ByteBuffer;

/**
 * 所有指令的父类，有获取操作数（读码）和执行两个方法
 * 不同指令获取操作数和执行的具体形式不同，由子类实现
 */
public abstract class Instruction {

    public abstract void fetchOperands(ByteBuffer codeReader);

    public abstract void execute(StackFrame topStackFrame);

}
