package com.njuse.jvmfinal.datastruct.array;

/**
 * 数组类型，建立类型和值的对应关系
 */
public class ArrayType {

    /**
     * 私有化构造方法，不允许创建实例
     */
    private ArrayType() {}

    public static final int T_BOOLEAN = 4;
    public static final int T_CHAR = 5;
    public static final int T_FLOAT = 6;
    public static final int T_DOUBLE = 7;
    public static final int T_BYTE = 8;
    public static final int T_SHORT = 9;
    public static final int T_INT = 10;
    public static final int T_LONG = 11;

    public static final int T_REF = 666;    /**自定义*/

    public static String getName(int type) {
        switch (type) {
            case T_BOOLEAN:
                return "boolean";
            case T_CHAR:
                return "char";
            case T_FLOAT:
                return "float";
            case T_DOUBLE:
                return "double";
            case T_BYTE:
                return "byte";
            case T_SHORT:
                return "short";
            case T_INT:
                return "int";
            case T_LONG:
                return "long";
            case T_REF:
                return "ref";
            default:
                return "";
        }
    }

}
