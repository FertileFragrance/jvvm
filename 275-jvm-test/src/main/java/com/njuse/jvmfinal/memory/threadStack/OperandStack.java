package com.njuse.jvmfinal.memory.threadStack;

import com.njuse.jvmfinal.datastruct.JObject;
import com.njuse.jvmfinal.datastruct.Slot;

/**
 * 此类表示操作数栈对象，被栈帧组合
 */
public class OperandStack {

    private int maxSize;    /**槽的最大容量*/
    private Slot[] slots;   /**槽对象，存放数值*/
    private int top;        /**栈顶*/

    public OperandStack(int maxSize) {
        this.maxSize = maxSize;
        this.slots = new Slot[maxSize];
        for (int i = 0; i < this.maxSize; i++) {
            this.slots[i] = new Slot();
        }
        this.top = 0;
    }

    public void pushInt(int value) {
        slots[top].setValue(value);
        top++;
    }

    public int popInt() {
        top--;
        int ret = slots[top].getValue();
        slots[top] = new Slot();
        return ret;
    }

    public void pushFloat(float value) {
        slots[top].setValue(Float.floatToIntBits(value));
        top++;
    }

    public float popFloat() {
        top--;
        float ret = Float.intBitsToFloat(slots[top].getValue());
        slots[top] = new Slot();
        return ret;
    }

    public void pushDouble(double value) {
        pushLong(Double.doubleToLongBits(value));
    }

    public double popDouble() {
        return Double.longBitsToDouble(popLong());
    }

    public void pushLong(long value) {
        slots[top].setValue((int) value);
        top++;
        slots[top].setValue((int) (value >> 32));
        top++;
    }

    public long popLong() {
        top--;
        int high = slots[top].getValue();
        slots[top] = new Slot();
        top--;
        int low = slots[top].getValue();
        slots[top] = new Slot();
        return (((long) high) << 32) | ((long) low & 0x0ffffffffL);
    }

    public void pushObject(JObject object) {
        slots[top].setObject(object);
        top++;
    }

    public JObject popObject() {
        top--;
        JObject ret = slots[top].getObject();
        slots[top] = new Slot();
        return ret;
    }

    public void pushSlot(Slot slot) {
        slots[top] = slot;
        top++;
    }

    public Slot popSlot() {
        top--;
        Slot ret = slots[top];
        slots[top] = new Slot();
        return ret;
    }

}
