package com.njuse.jvmfinal.execution;

import com.njuse.jvmfinal.instructions.abstractIns.Instruction;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.threadStack.StackFrame;
import com.njuse.jvmfinal.memory.threadStack.ThreadStack;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * 解释器类
 */
public class Interpreter {

    private static ByteBuffer codeReader;   /**用于读取指令*/

    /**
     * 构造方法私有，不允许创建该类的实例
     * 该类当做工具类来用
     */
    private Interpreter() {}

    /**
     * 解释器执行的入口
     * @param threadStack 开始执行的线程栈
     */
    public static void interpret(ThreadStack threadStack) {
        initCodeReader(threadStack);
        File file = new File("./src/main/java/com/njuse/jvmfinal/classloader/ClassLoader.java");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write("############################The start!###############################\n");
            bw.flush();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        loop(threadStack, bw);
    }

    /**
     * 给codeReader设置PC
     * @param threadStack 正在执行的线程栈
     */
    private static void initCodeReader(ThreadStack threadStack) {
        byte[] code = threadStack.getTopStackFrame().getMethod().getCode();
        codeReader = ByteBuffer.wrap(code);
        int nextPC = threadStack.getTopStackFrame().getNextPC();
        codeReader.position(nextPC);
    }

    /**
     * 解释器的主循环，主要流程是：
     * 1. 得到顶层的栈帧（即正在执行方法的栈帧）
     * 2. 根据栈帧中的PC值利用codeReader从方法中取出指令码
     * 3. 把指令码交给译码器译码成指令，指令获取操作数并重新设置PC
     * 4. 执行指令，得到执行指令后的新的顶层栈帧，若是空则已全部执行完，退出循环
     * 5. 不是空则与原来的顶层栈帧比较，如果不同则需要重新设置codeReader
     * 6. 一次循环结束，下一次开始重新获得顶层栈帧继续执行
     * @param threadStack 正在执行的线程栈
     */
    private static void loop(ThreadStack threadStack, BufferedWriter bw) {
        while (true) {
            StackFrame topStackFrame = threadStack.getTopStackFrame();
            Method method = topStackFrame.getMethod();
            if (!method.isHasParsed()) {
                method.parseCode();
            }
            codeReader.position(topStackFrame.getNextPC());
            int opCode = codeReader.get() & 0xff;
            Instruction instruction = Decoder.decode(opCode);
            instruction.fetchOperands(codeReader);
            int nextPC = codeReader.position();
            topStackFrame.setNextPC(nextPC);
            //System.out.printf("%-30s", instruction);
            //System.out.println(threadStack.getTopStackFrame().getMethod().getClazz().getName());
            try {
                bw.write(String.valueOf(instruction));
                for (int i = 0; i < 30 - String.valueOf(instruction).length(); i++) {
                    bw.write(' ');
                }
                bw.write(threadStack.getTopStackFrame().getMethod().getClazz().getName() + "\n");
                bw.flush();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            instruction.execute(topStackFrame);
            StackFrame newTopStackFrame = threadStack.getTopStackFrame();
            if (newTopStackFrame == null) {
                try {
                    bw.write("############################The end!#################################\n\n");
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
                return;
            }
            if (newTopStackFrame != topStackFrame) {
                initCodeReader(threadStack);
            }
        }
    }

}
