package com.te2.dto;

import com.te2.data.MessageType;

/**
 * Created by jasonskipper on 4/17/17.  This
 */
public class SubscriptionStats {
    private MessageType type;
    private long readCount;

    public SubscriptionStats(MessageType type, long readCount) {
        this.type = type;
        this.readCount = readCount;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public long getReadCount() {
        return readCount;
    }

    public void setReadCount(long readCount) {
        this.readCount = readCount;
    }
}
