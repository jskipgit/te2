package com.te2.dto;

import com.te2.data.Message;
import org.springframework.data.domain.Page;

import java.util.Collection;

/**
 * Created by jasonskipper on 4/17/17.
 */
public class SubscriptionRead {

    private Page<Message> messages;
    private Collection<SubscriptionStats> stats;

    public Page<Message> getMessages() {
        return messages;
    }

    public void setMessages(Page<Message> messages) {
        this.messages = messages;
    }

    public Collection<SubscriptionStats> getStats() {
        return stats;
    }

    public void setStats(Collection<SubscriptionStats> stats) {
        this.stats = stats;
    }
}
