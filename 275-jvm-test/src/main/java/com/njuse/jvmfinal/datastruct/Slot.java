package com.njuse.jvmfinal.datastruct;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
/**
 * 此类作为槽对象，是Vars结构中的主要成员，类似于联合体
 */
public class Slot {

    private JObject object;
    private Integer value;

    @Override
    public Slot clone() {
        Slot slot = new Slot();
        slot.object = this.object;
        slot.value = this.value;
        return slot;
    }

}
