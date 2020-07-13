package com.njuse.jvmfinal;

import com.njuse.jvmfinal.classloader.ClassLoader;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;
import com.njuse.jvmfinal.memory.threadStack.ThreadStack;
import com.njuse.jvmfinal.util.IOUtil;

public class Starter {
    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoader.getInstance();
        //JClass jClass = classLoader.loadClass("cases/light/LightEasyStaticTest", "src/test/java", null);
        //JClass jClass = classLoader.loadClass("java/lang/Object", "", null);
        JClass jClass = classLoader.loadClass("cases/medium/MediumTest", "src/test/java", null);
        ThreadStack threadStack = new ThreadStack();
        Method mainMethod = jClass.getMainMethod();
        if (mainMethod == null) {
            return;
        }
        StackFrame mainMethodStackFrame = new StackFrame(threadStack,
                mainMethod, mainMethod.getMaxLocal(), mainMethod.getMaxStack());
        threadStack.pushStackFrame(mainMethodStackFrame);
        jClass.initClass(threadStack, jClass);
        Interpreter.interpret(threadStack);
        System.out.println(Interpreter.message);
    }

    /**
     * ⚠️警告：不要改动这个方法签名，这是和测试用例的唯一接口
     */
    public static void runTest(String mainClassName, String cp) {
        ClassLoader classLoader = ClassLoader.getInstance();
        JClass startClass = classLoader.loadClass(IOUtil.dotToSeparator(mainClassName), cp, null);
        ThreadStack threadStack = new ThreadStack();
        Method mainMethod = startClass.getMainMethod();
        StackFrame mainMethodStackFrame = new StackFrame(threadStack,
                mainMethod, mainMethod.getMaxLocal(), mainMethod.getMaxStack());
        threadStack.pushStackFrame(mainMethodStackFrame);
        startClass.initClass(threadStack, startClass);
        Interpreter.interpret(threadStack);
    }

}
