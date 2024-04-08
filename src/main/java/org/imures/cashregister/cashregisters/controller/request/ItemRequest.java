package org.imures.cashregister.cashregisters.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.imures.cashregister.cashregisters.entity.Characteristic;
import org.imures.cashregister.type.entity.Type;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class ItemRequest {

    private String name;
    private String typeName;
    private String generalInfo;
    private String description;
    private HashMap<String, String> characteristics;

    public Set<Characteristic> getCharacteristics() {

        Set<Characteristic> characteristics = new HashSet<>();
        for(Map.Entry<String, String> entry : this.characteristics.entrySet()){
            Characteristic characteristic = new Characteristic();
            characteristic.setKey(entry.getKey());
            characteristic.setValue(entry.getValue());
            characteristics.add(characteristic);
        }

        return characteristics;
    }

}
