package com.njuse.jvmfinal.instructions.control.conditional;

import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.instructions.abstractIns.BranchInstruction;
import com.njuse.jvmfinal.memory.threadStack.OperandStack;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;

/**
 * 所有if_icmp<cond>指令的父类
 */
public abstract class IF_ICMPCOND extends BranchInstruction {

    /**
     * 所有取两个int类型值的条件分支指令执行的方法，根据具体不同的指令要求判断PC值要不要更改
     * @param topStackFrame 顶层栈帧
     */
    @Override
    public void execute(StackFrame topStackFrame) {
        OperandStack operandStack = topStackFrame.getOperandStack();
        int val2 = operandStack.popInt();
        int val1 = operandStack.popInt();
        Interpreter.message += this.toString() + "\t" + topStackFrame.getMethod().getClazz().getName() + "\t" +
                topStackFrame.getMethod().getName() + "\n";
        if (condition(val1, val2)) {
            int branchPC = topStackFrame.getNextPC() - 3 + this.offset;
            topStackFrame.setNextPC(branchPC);
        }
    }

    /**
     * 由子类具体实现的条件判断方法
     * @param val1 操作数栈取下的int类型数值
     * @param val2 操作数栈取下的int类型数值
     * @return 真或假，是真则改变PC值
     */
    protected abstract boolean condition(int val1, int val2);

}
