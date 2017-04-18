package com.te2.data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jasonskipper on 4/17/17.
 */
@Entity
public class Subscription {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ElementCollection(targetClass=MessageType.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<MessageType> types;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<MessageType> getTypes() {
        return types;
    }

    public void setTypes(List<MessageType> types) {
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
