package com.njuse.jvmfinal.datastruct;

/**
 * 此抽象类为局部变量表和静态变量提供统一的槽位
 */
public abstract class Vars {

    protected int size;
    protected Slot[] slots;

    public Vars(int size) {
        this.size = size;
        slots = new Slot[size];
        for (int i = 0; i < this.size; i++) {
            slots[i] = new Slot();
        }
    }

    public void setInt(int index, int value) {
        slots[index].setValue(value);
    }

    public int getInt(int index) {
        return slots[index].getValue();
    }

    public void setFloat(int index, float value) {
        slots[index].setValue(Float.floatToIntBits(value));
    }

    public float getFloat(int index) {
        return Float.intBitsToFloat(slots[index].getValue());
    }

    public void setDouble(int index, double value) {
        setLong(index, Double.doubleToLongBits(value));
    }

    public double getDouble(int index) {
        return Double.longBitsToDouble(getLong(index));
    }

    public void setLong(int index, long value) {
        slots[index].setValue((int) value);
        slots[index+1].setValue((int) (value >> 32));
    }

    public long getLong(int index) {
        int low = slots[index].getValue();
        int high = slots[index+1].getValue();
        return (((long) high) << 32) | ((long) low & 0x0ffffffffL);
    }

    public void setObject(int index, JObject object) {
        slots[index].setObject(object);
    }

    public JObject getObject(int index) {
        return slots[index].getObject();
    }

    public void setSlot(int index, Slot slot) {
        slots[index] = slot;
    }

}
