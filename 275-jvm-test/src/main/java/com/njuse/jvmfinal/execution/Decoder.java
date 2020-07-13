package com.njuse.jvmfinal.execution;

import com.njuse.jvmfinal.instructions.InstructionSet;
import com.njuse.jvmfinal.instructions.abstractIns.Instruction;
import com.njuse.jvmfinal.instructions.control.conditional.*;
import com.njuse.jvmfinal.instructions.control.unconditional.*;
import com.njuse.jvmfinal.instructions.convert.fromDouble.*;
import com.njuse.jvmfinal.instructions.convert.fromFloat.*;
import com.njuse.jvmfinal.instructions.convert.fromInt.*;
import com.njuse.jvmfinal.instructions.convert.fromLong.*;
import com.njuse.jvmfinal.instructions.invoke.*;
import com.njuse.jvmfinal.instructions.load.constValue.*;
import com.njuse.jvmfinal.instructions.load.fromLocalVariableTable.*;
import com.njuse.jvmfinal.instructions.object.NEW;
import com.njuse.jvmfinal.instructions.object.accessField.GETFIELD;
import com.njuse.jvmfinal.instructions.object.accessField.GETSTATIC;
import com.njuse.jvmfinal.instructions.object.accessField.PUTFIELD;
import com.njuse.jvmfinal.instructions.object.accessField.PUTSTATIC;
import com.njuse.jvmfinal.instructions.operate.IINC;
import com.njuse.jvmfinal.instructions.operate.add.*;
import com.njuse.jvmfinal.instructions.operate.bitwise.*;
import com.njuse.jvmfinal.instructions.operate.compare.*;
import com.njuse.jvmfinal.instructions.operate.div.*;
import com.njuse.jvmfinal.instructions.operate.mul.*;
import com.njuse.jvmfinal.instructions.operate.neg.*;
import com.njuse.jvmfinal.instructions.operate.rem.*;
import com.njuse.jvmfinal.instructions.operate.shift.*;
import com.njuse.jvmfinal.instructions.operate.sub.*;
import com.njuse.jvmfinal.instructions.opndstack.*;
import com.njuse.jvmfinal.instructions.ret.*;
import com.njuse.jvmfinal.instructions.store.*;

import java.util.HashMap;

/**
 * 译码器类，将读取到的int类型数据（指令码）转换成指令
 */
public class Decoder {

    /**
     * 私有化构造方法，不允许创建实例
     */
    private Decoder() {}

    private static HashMap<Integer, Instruction> opMap; /**建立指令码到指令的映射*/

    /**
     * 此方法用于译码
     * @param opCode 指令码
     * @return 指令码对应的指令
     */
    public static Instruction decode(int opCode) {
        return opMap.get(opCode);
    }

    static {
        opMap = new HashMap<>();
        // TODO 将指令码和指令对象放入集合中
        //opMap.put(InstructionSet.NOP, new NOP())
        opMap.put(InstructionSet.ACONST_NULL, new ACONST_NULL());
        opMap.put(InstructionSet.ICONST_M1, new ICONST_N(-1));
        opMap.put(InstructionSet.ICONST_0, new ICONST_N(0));
        opMap.put(InstructionSet.ICONST_1, new ICONST_N(1));
        opMap.put(InstructionSet.ICONST_2, new ICONST_N(2));
        opMap.put(InstructionSet.ICONST_3, new ICONST_N(3));
        opMap.put(InstructionSet.ICONST_4, new ICONST_N(4));
        opMap.put(InstructionSet.ICONST_5, new ICONST_N(5));
        opMap.put(InstructionSet.LCONST_0, new LCONST_N(0));
        opMap.put(InstructionSet.LCONST_1, new LCONST_N(1));
        opMap.put(InstructionSet.FCONST_0, new FCONST_N(0));
        opMap.put(InstructionSet.FCONST_1, new FCONST_N(1));
        opMap.put(InstructionSet.FCONST_2, new FCONST_N(2));
        opMap.put(InstructionSet.DCONST_0, new DCONST_N(0));
        opMap.put(InstructionSet.DCONST_1, new DCONST_N(1));
        opMap.put(InstructionSet.BIPUSH, new BIPUSH());
        opMap.put(InstructionSet.SIPUSH, new SIPUSH());
        opMap.put(InstructionSet.LDC, new LDC());
        opMap.put(InstructionSet.LDC_W, new LDC_W());
        opMap.put(InstructionSet.LDC2_W, new LDC2_W());
        opMap.put(InstructionSet.ILOAD, new ILOAD());
        opMap.put(InstructionSet.LLOAD, new LLOAD());
        opMap.put(InstructionSet.FLOAD, new FLOAD());
        opMap.put(InstructionSet.DLOAD, new DLOAD());
        opMap.put(InstructionSet.ALOAD, new ALOAD());
        opMap.put(InstructionSet.ILOAD_0, new ILOAD_N(0));
        opMap.put(InstructionSet.ILOAD_1, new ILOAD_N(1));
        opMap.put(InstructionSet.ILOAD_2, new ILOAD_N(2));
        opMap.put(InstructionSet.ILOAD_3, new ILOAD_N(3));
        opMap.put(InstructionSet.LLOAD_0, new LLOAD_N(0));
        opMap.put(InstructionSet.LLOAD_1, new LLOAD_N(1));
        opMap.put(InstructionSet.LLOAD_2, new LLOAD_N(2));
        opMap.put(InstructionSet.LLOAD_3, new LLOAD_N(3));
        opMap.put(InstructionSet.FLOAD_0, new FLOAD_N(0));
        opMap.put(InstructionSet.FLOAD_1, new FLOAD_N(1));
        opMap.put(InstructionSet.FLOAD_2, new FLOAD_N(2));
        opMap.put(InstructionSet.FLOAD_3, new FLOAD_N(3));
        opMap.put(InstructionSet.DLOAD_0, new DLOAD_N(0));
        opMap.put(InstructionSet.DLOAD_1, new DLOAD_N(1));
        opMap.put(InstructionSet.DLOAD_2, new DLOAD_N(2));
        opMap.put(InstructionSet.DLOAD_3, new DLOAD_N(3));
        opMap.put(InstructionSet.ALOAD_0, new ALOAD_N(0));
        opMap.put(InstructionSet.ALOAD_1, new ALOAD_N(1));
        opMap.put(InstructionSet.ALOAD_2, new ALOAD_N(2));
        opMap.put(InstructionSet.ALOAD_3, new ALOAD_N(3));

        opMap.put(InstructionSet.ISTORE, new ISTORE());
        opMap.put(InstructionSet.LSTORE, new LSTORE());
        opMap.put(InstructionSet.FSTORE, new FSTORE());
        opMap.put(InstructionSet.DSTORE, new DSTORE());
        opMap.put(InstructionSet.ASTORE, new ASTORE());
        opMap.put(InstructionSet.ISTORE_0, new ISTORE_N(0));
        opMap.put(InstructionSet.ISTORE_1, new ISTORE_N(1));
        opMap.put(InstructionSet.ISTORE_2, new ISTORE_N(2));
        opMap.put(InstructionSet.ISTORE_3, new ISTORE_N(3));
        opMap.put(InstructionSet.LSTORE_0, new LSTORE_N(0));
        opMap.put(InstructionSet.LSTORE_1, new LSTORE_N(1));
        opMap.put(InstructionSet.LSTORE_2, new LSTORE_N(2));
        opMap.put(InstructionSet.LSTORE_3, new LSTORE_N(3));
        opMap.put(InstructionSet.FSTORE_0, new FSTORE_N(0));
        opMap.put(InstructionSet.FSTORE_1, new FSTORE_N(1));
        opMap.put(InstructionSet.FSTORE_2, new FSTORE_N(2));
        opMap.put(InstructionSet.FSTORE_3, new FSTORE_N(3));
        opMap.put(InstructionSet.DSTORE_0, new DSTORE_N(0));
        opMap.put(InstructionSet.DSTORE_1, new DSTORE_N(1));
        opMap.put(InstructionSet.DSTORE_2, new DSTORE_N(2));
        opMap.put(InstructionSet.DSTORE_3, new DSTORE_N(3));
        opMap.put(InstructionSet.ASTORE_0, new ASTORE_N(0));
        opMap.put(InstructionSet.ASTORE_1, new ASTORE_N(1));
        opMap.put(InstructionSet.ASTORE_2, new ASTORE_N(2));
        opMap.put(InstructionSet.ASTORE_3, new ASTORE_N(3));

        opMap.put(InstructionSet.POP, new POP());
        opMap.put(InstructionSet.POP2, new POP2());
        opMap.put(InstructionSet.DUP, new DUP());
        opMap.put(InstructionSet.DUP_X1, new DUP_X1());
        opMap.put(InstructionSet.DUP_X2, new DUP_X2());
        opMap.put(InstructionSet.DUP2, new DUP2());
        opMap.put(InstructionSet.DUP2_X1, new DUP2_X1());
        opMap.put(InstructionSet.DUP2_X2, new DUP2_X2());
        opMap.put(InstructionSet.SWAP, new SWAP());
        opMap.put(InstructionSet.IADD, new IADD());
        opMap.put(InstructionSet.LADD, new LADD());
        opMap.put(InstructionSet.FADD, new FADD());
        opMap.put(InstructionSet.DADD, new DADD());
        opMap.put(InstructionSet.ISUB, new ISUB());
        opMap.put(InstructionSet.LSUB, new LSUB());
        opMap.put(InstructionSet.FSUB, new FSUB());
        opMap.put(InstructionSet.DSUB, new DSUB());
        opMap.put(InstructionSet.IMUL, new IMUL());
        opMap.put(InstructionSet.LMUL, new LMUL());
        opMap.put(InstructionSet.FMUL, new FMUL());
        opMap.put(InstructionSet.DMUL, new DMUL());
        opMap.put(InstructionSet.IDIV, new IDIV());
        opMap.put(InstructionSet.LDIV, new LDIV());
        opMap.put(InstructionSet.FDIV, new FDIV());
        opMap.put(InstructionSet.DDIV, new DDIV());
        opMap.put(InstructionSet.IREM, new IREM());
        opMap.put(InstructionSet.LREM, new LREM());
        opMap.put(InstructionSet.FREM, new FREM());
        opMap.put(InstructionSet.DREM, new DREM());
        opMap.put(InstructionSet.INEG, new INEG());
        opMap.put(InstructionSet.LNEG, new LNEG());
        opMap.put(InstructionSet.FNEG, new FNEG());
        opMap.put(InstructionSet.DNEG, new DNEG());
        opMap.put(InstructionSet.ISHL, new ISHL());
        opMap.put(InstructionSet.LSHL, new LSHL());
        opMap.put(InstructionSet.ISHR, new ISHR());
        opMap.put(InstructionSet.LSHR, new LSHR());
        opMap.put(InstructionSet.IUSHR, new IUSHR());
        opMap.put(InstructionSet.LUSHR, new LUSHR());
        opMap.put(InstructionSet.IAND, new IAND());
        opMap.put(InstructionSet.LAND, new LAND());
        opMap.put(InstructionSet.IOR, new IOR());
        opMap.put(InstructionSet.LOR, new LOR());
        opMap.put(InstructionSet.IXOR, new IXOR());
        opMap.put(InstructionSet.LXOR, new LXOR());
        opMap.put(InstructionSet.IINC, new IINC());
        opMap.put(InstructionSet.I2L, new I2L());
        opMap.put(InstructionSet.I2F, new I2F());
        opMap.put(InstructionSet.I2D, new I2D());
        opMap.put(InstructionSet.L2I, new L2I());
        opMap.put(InstructionSet.L2F, new L2F());
        opMap.put(InstructionSet.L2D, new L2D());
        opMap.put(InstructionSet.F2I, new F2I());
        opMap.put(InstructionSet.F2L, new F2L());
        opMap.put(InstructionSet.F2D, new F2D());
        opMap.put(InstructionSet.D2I, new D2I());
        opMap.put(InstructionSet.D2L, new D2L());
        opMap.put(InstructionSet.D2F, new D2F());
        opMap.put(InstructionSet.I2B, new I2B());
        opMap.put(InstructionSet.I2C, new I2C());
        opMap.put(InstructionSet.I2S, new I2S());
        opMap.put(InstructionSet.LCMP, new LCMP());
        opMap.put(InstructionSet.FCMPL, new FCMPL());
        opMap.put(InstructionSet.FCMPG, new FCMPG());
        opMap.put(InstructionSet.DCMPL, new DCMPL());
        opMap.put(InstructionSet.DCMPG, new DCMPG());
        opMap.put(InstructionSet.IFEQ, new IFEQ());
        opMap.put(InstructionSet.IFNE, new IFNE());
        opMap.put(InstructionSet.IFLT, new IFLT());
        opMap.put(InstructionSet.IFGE, new IFGE());
        opMap.put(InstructionSet.IFGT, new IFGT());
        opMap.put(InstructionSet.IFLE, new IFLE());
        opMap.put(InstructionSet.IF_ICMPEQ, new IF_ICMPEQ());
        opMap.put(InstructionSet.IF_ICMPNE, new IF_ICMPNE());
        opMap.put(InstructionSet.IF_ICMPLT, new IF_ICMPLT());
        opMap.put(InstructionSet.IF_ICMPGE, new IF_ICMPGE());
        opMap.put(InstructionSet.IF_ICMPGT, new IF_ICMPGT());
        opMap.put(InstructionSet.IF_ICMPLE, new IF_ICMPLE());
        opMap.put(InstructionSet.IF_ACMPEQ, new IF_ACMPEQ());
        opMap.put(InstructionSet.IF_ACMPNE, new IF_ACMPNE());
        opMap.put(InstructionSet.GOTO, new GOTO());
        opMap.put(InstructionSet.JSR, new JSR());
        opMap.put(InstructionSet.RET, new RET());
        //opMap.put(InstructionSet.TABLESWITCH, new TABLESWITCH());
        //opMap.put(InstructionSet.LOOKUPSWITCH, new LOOKUPSWITCH());
        opMap.put(InstructionSet.IRETURN, new IRETURN());
        opMap.put(InstructionSet.LRETURN, new LRETURN());
        opMap.put(InstructionSet.FRETURN, new FRETURN());
        opMap.put(InstructionSet.DRETURN, new DRETURN());
        opMap.put(InstructionSet.ARETURN, new ARETURN());
        opMap.put(InstructionSet.RETURN, new RETURN());
        opMap.put(InstructionSet.GETSTATIC, new GETSTATIC());
        opMap.put(InstructionSet.PUTSTATIC, new PUTSTATIC());
        opMap.put(InstructionSet.GETFIELD, new GETFIELD());
        opMap.put(InstructionSet.PUTFIELD, new PUTFIELD());
        opMap.put(InstructionSet.INVOKEVIRTUAL, new INVOKEVIRTUAL());
        opMap.put(InstructionSet.INVOKESPECIAL, new INVOKESPECIAL());
        opMap.put(InstructionSet.INVOKESTATIC, new INVOKESTATIC());
        opMap.put(InstructionSet.INVOKEINTERFACE, new INVOKEINTERFACE());
        //opMap.put(InstructionSet.INVOKEDYNAMIC, new INVOKEDYNAMIC());
        opMap.put(InstructionSet.NEW, new NEW());

        opMap.put(InstructionSet.IFNULL, new IFNULL());
        opMap.put(InstructionSet.IFNONNULL, new IFNONNULL());
        opMap.put(InstructionSet.GOTO_W, new GOTO_W());
        opMap.put(InstructionSet.JSR_W, new JSR_W());
    }

}
