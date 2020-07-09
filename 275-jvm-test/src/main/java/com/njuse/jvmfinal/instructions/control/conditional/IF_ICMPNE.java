package com.njuse.jvmfinal.instructions.control.conditional;

public class IF_ICMPNE extends IF_ICMPCOND {

    /**
     * 判断两个值是否不相等
     * @param val1 操作数栈取下的int类型数值
     * @param val2 操作数栈取下的int类型数值
     * @return 真或假
     */
    @Override
    protected boolean condition(int val1, int val2) {
        return val1 != val2;
    }

}
