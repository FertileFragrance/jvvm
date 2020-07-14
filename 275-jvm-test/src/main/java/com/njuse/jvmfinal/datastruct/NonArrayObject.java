package com.njuse.jvmfinal.datastruct;

import com.njuse.jvmfinal.memory.heap.InstanceVars;
import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;

import java.util.Arrays;

@Getter
/**
 * 此类表示非数组的对象，存有实例变量
 */
public class NonArrayObject extends JObject {

    private InstanceVars instanceVars;  /**该对象的实例变量，结构类似于类的静态变量*/

    public NonArrayObject(JClass jClass) {
        this.jClass = jClass;
        instanceVars = new InstanceVars(jClass.getInstanceSlotCount());
        loadDefaultValue(jClass);
    }

    private void loadDefaultValue(JClass clazz) {
        do {
            Arrays.stream(clazz.getFields())
                    .filter(f -> !f.isStatic())
                    .forEach(f -> {
                        String descriptor = f.getDescriptor();
                        switch (descriptor.charAt(0)) {
                            case 'B':
                                this.instanceVars.setInt(f.getSlotID(), 0);
                                break;
                            case 'C':
                                this.instanceVars.setInt(f.getSlotID(), 0);
                                break;
                            case 'D':
                                this.instanceVars.setDouble(f.getSlotID(), 0.0d);
                                break;
                            case 'F':
                                this.instanceVars.setFloat(f.getSlotID(), 0.0f);
                                break;
                            case 'I':
                                this.instanceVars.setInt(f.getSlotID(), 0);
                                break;
                            case 'J':
                                this.instanceVars.setLong(f.getSlotID(), 0L);
                                break;
                            case 'S':
                                this.instanceVars.setInt(f.getSlotID(), 0);
                                break;
                            case 'Z':
                                this.instanceVars.setInt(f.getSlotID(), 0);
                                break;
                            case 'L':
                                this.instanceVars.setObject(f.getSlotID(), new NullObject());
                                break;
                            default:
                                break;
                        }
                    });
            clazz = clazz.getSuperClass();
        } while (clazz != null);
    }

}
