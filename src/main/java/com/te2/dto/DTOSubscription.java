package com.te2.dto;

import com.te2.data.MessageType;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import java.util.List;

/**
 * Created by jasonskipper on 4/18/17.
 */
public class DTOSubscription {
    private String name;
    private List<MessageType> types;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MessageType> getTypes() {
        return types;
    }

    public void setTypes(List<MessageType> types) {
        this.types = types;
    }
}
