package org.imures.cashregister.cashregisters.controller.response;

import lombok.Getter;
import lombok.Setter;
import org.imures.cashregister.cashregisters.entity.Characteristic;
import org.imures.cashregister.type.controller.reponse.TypeResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class ItemResponse {

    private String name;
    private String type;
    private String generalInfo;
    private String description;
    private Map<String, String> characteristics;

    public void setCharacteristics(Set<Characteristic> characteristics) {
        this.characteristics = new HashMap<>();
        for(Characteristic characteristic : characteristics){
            this.characteristics.put(characteristic.getKey(), characteristic.getValue());
        }
    }

    public void setType(TypeResponse type) {
        this.type = type.getType();
    }
}
