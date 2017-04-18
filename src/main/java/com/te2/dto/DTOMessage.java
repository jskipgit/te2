package com.te2.dto;

import com.te2.data.MessageType;

/**
 * Created by jasonskipper on 4/18/17.
 */
public class DTOMessage {
    private String content;
    private MessageType type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
