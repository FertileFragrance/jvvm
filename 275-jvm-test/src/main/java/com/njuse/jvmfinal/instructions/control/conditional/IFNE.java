package com.njuse.jvmfinal.instructions.control.conditional;

public class IFNE extends IFCOND {

    /**
     * 判断值是否不为0
     * @param val 值
     * @return 真或假
     */
    @Override
    public boolean condition(int val) {
        return val != 0;
    }

}
