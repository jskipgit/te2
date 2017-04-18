package com.te2.data;

import org.hibernate.annotations.Fetch;

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

    @ElementCollection(targetClass=MessageType.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<MessageType> subs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<MessageType> getSubs() {
        return subs;
    }

    public void setSubs(List<MessageType> subs) {
        this.subs = subs;
    }
}
