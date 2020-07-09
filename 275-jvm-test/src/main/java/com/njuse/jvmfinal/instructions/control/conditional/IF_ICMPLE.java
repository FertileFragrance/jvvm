package com.njuse.jvmfinal.instructions.control.conditional;

public class IF_ICMPLE extends IF_ICMPCOND {

    /**
     * 判断val1是否小于等于val2
     * @param val1 操作数栈取下的int类型数值
     * @param val2 操作数栈取下的int类型数值
     * @return 真或假
     */
    @Override
    protected boolean condition(int val1, int val2) {
        return val1 <= val2;
    }

}
