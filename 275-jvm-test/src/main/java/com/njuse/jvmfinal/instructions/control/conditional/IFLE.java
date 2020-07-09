package com.njuse.jvmfinal.instructions.control.conditional;

public class IFLE extends IFCOND {

    /**
     * 判断值是否小于等于0
     * @param val 值
     * @return 真或假
     */
    @Override
    public boolean condition(int val) {
        return val <= 0;
    }

}
