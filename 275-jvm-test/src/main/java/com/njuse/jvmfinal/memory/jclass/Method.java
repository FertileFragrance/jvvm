package com.njuse.jvmfinal.memory.jclass;

import com.njuse.jvmfinal.classloader.classfileparser.MethodInfo;
import com.njuse.jvmfinal.classloader.classfileparser.attribute.CodeAttribute;
import com.njuse.jvmfinal.execution.Decoder;
import com.njuse.jvmfinal.instructions.abstractIns.Instruction;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.ArrayList;


@Getter
@Setter
public class Method extends ClassMember {
    private int maxStack;                   // 方法的栈帧中操作数栈所需要的槽的个数
    private int maxLocal;                   // 方法的栈帧中局部变量表所需要的槽的个数
    private int argc;                       // 方法中的参数所占据的空间（即槽的个数）
    private byte[] code;                    // 方法代码的字节表示
    private ArrayList<String> instList;     //
    boolean hasParsed = false;              // 该方法是否已经被解析成指令

    /*
        构造方法，将方法表对象转换为方法对象，完成了：
            1. 根据方法表直接获得方法的访问标志、方法名、描述符号和方法所在的类
            2. 根据方法表中的代码属性获得方法的代码的字节表示形式，即code
            3. 同时获得了该方法的栈帧中操作数栈和局部变量表所需要的槽的个数
        注意，code要转换为字节码指令以继续执行
     */
    public Method(MethodInfo info, JClass clazz) {
        this.clazz = clazz;
        accessFlags = info.getAccessFlags();
        name = info.getName();
        descriptor = info.getDescriptor();

        CodeAttribute codeAttribute = info.getCodeAttribute();
        if (codeAttribute != null) {
            maxLocal = codeAttribute.getMaxLocal();
            maxStack = codeAttribute.getMaxStack();
            code = codeAttribute.getCode();
        }
        argc = calculateArgcFromDescriptor(descriptor);
    }

    private int calculateArgcFromDescriptor(String descriptor) {
        char[] chars = descriptor.toCharArray();
        int maxIndex = descriptor.lastIndexOf(')');
        assert maxIndex != -1;
        int idx = descriptor.indexOf('(');
        assert idx != -1;
        //skip the index of '('
        idx++;
        int cnt = 0;
        while (idx + 1 <= maxIndex) {
            switch (chars[idx++]) {
                case 'J':
                case 'D':
                    cnt++;
                    //fall through
                case 'F':
                case 'I':
                case 'B':
                case 'C':
                case 'S':
                case 'Z':
                    cnt++;
                    break;
                case 'L':
                    cnt++;
                    while (idx < maxIndex && chars[idx++] != ';') ;
                    break;
                case '[':
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown descriptor!");
            }
        }
        return cnt;
    }

    /**
     * 根据方法访问标志判断该方法是否被某个修饰符修饰
     * @return 是或否
     */
    public boolean isSynchronized() {
        return (this.accessFlags & AccessFlags.ACC_SYNCHRONIZED) != 0;
    }

    public boolean isBridge() {
        return (this.accessFlags & AccessFlags.ACC_BRIDGE) != 0;
    }

    public boolean isVarargs() {
        return (this.accessFlags & AccessFlags.ACC_VARARGS) != 0;
    }

    public boolean isNative() {
        return (this.accessFlags & AccessFlags.ACC_NATIVE) != 0;
    }

    public boolean isAbstract() {
        return (this.accessFlags & AccessFlags.ACC_ABSTRACT) != 0;
    }

    public boolean isStrict() {
        return (this.accessFlags & AccessFlags.ACC_STRICT) != 0;
    }

    /**
     * 此方法用于解析方法代码
     */
    public void parseCode() {
        ByteBuffer codeReader = ByteBuffer.wrap(this.code);
        int position = 0;
        codeReader.position(position);
        int size = this.code.length;
        for(this.instList = new ArrayList<>(); position < size; position = codeReader.position()) {
            int opcode = codeReader.get() & 0xff;
            Instruction instruction = Decoder.decode(opcode);
            instruction.fetchOperands(codeReader);
            this.instList.add(position + " " + instruction.toString());
            //System.out.println(position + " " + instruction + " " + this.clazz.getName());
        }
        hasParsed = true;
    }

}
