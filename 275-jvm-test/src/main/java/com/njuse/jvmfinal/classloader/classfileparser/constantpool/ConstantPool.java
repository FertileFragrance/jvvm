package com.njuse.jvmfinal.classloader.classfileparser.constantpool;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.ConstantPoolInfo;
import org.apache.commons.lang3.tuple.Pair;

public class ConstantPool {
    private ConstantPoolInfo[] infos;          // 一个常量池对象由且只由一些项目类型组成

    /*
        并非单例模式，而是每一个Class文件都有一个常量池对象
        每次调用该方法都会创建一个ConstantPool对象，里面包含各种常量信息
     */
    public static Pair<ConstantPool, Integer> getInstance(int constantPoolCnt, byte[] in, int offset) {
        ConstantPool constantPool = new ConstantPool();             // 创建新对象
        constantPool.infos = new ConstantPoolInfo[constantPoolCnt]; // 给新对象唯一的成员变量分配空间
        int currentOfs = offset;                                    // 得到缓冲区的偏移量
        int entryCnt = 0;
        while (entryCnt < constantPoolCnt - 1) {                    // 不断赋值，但最后留了一个空位，实际使用过程中作用等同于从1开始计数
            Pair<ConstantPoolInfo, Integer> infoInt = ConstantPoolInfo.getConstantPoolInfo(constantPool, in, currentOfs);
            ConstantPoolInfo info = infoInt.getKey();
            constantPool.infos[entryCnt] = info;
            entryCnt += info.getEntryLength();                      // 实际上就是1
            currentOfs += infoInt.getValue();
        }
        return Pair.of(constantPool, currentOfs - offset);
    }

    public ConstantPoolInfo get(int i) {
        if (i <= 0 || i > infos.length - 1) {
            throw new UnsupportedOperationException("Invalid CP index " + i);
        }

        ConstantPoolInfo info = infos[i - 1];                       // 这是常量池从1开始计数造成的
        if (info == null) {
            throw new UnsupportedOperationException("Invalid CP index " + i);
        }
        return info;
    }

    public ConstantPoolInfo[] getInfos() {
        return infos;
    }

}
