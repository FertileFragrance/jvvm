package com.njuse.jvmfinal.memory.threadStack;

import java.util.Stack;

/**
 * 此类表示一个线程私有的栈
 */
public class ThreadStack {

    private Stack<StackFrame> stackFrames;

    public ThreadStack() {
        this.stackFrames = new Stack<>();
    }

    public void pushStackFrame(StackFrame stackFrame) {
        this.stackFrames.push(stackFrame);
    }

    public StackFrame popStackFrame() {
        return this.stackFrames.pop();
    }

    public StackFrame getTopStackFrame() {
        if (stackFrames.empty()) {
            return null;
        }
        return this.stackFrames.lastElement();
    }

}
