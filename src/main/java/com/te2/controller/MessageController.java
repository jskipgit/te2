package com.te2.controller;

import com.te2.data.Message;
import com.te2.dto.DTOMessage;
import com.te2.exception.MessageMustHaveContentException;
import com.te2.exception.MessageMustHaveTypeException;
import com.te2.repo.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * Created by jasonskipper on 4/17/17.
 */
@RestController
@RequestMapping(path = "/te2/")
public class MessageController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping(value = "messages", method = RequestMethod.POST)
    public Message create(@RequestBody DTOMessage postMe) {
        log.debug("create message called with:"+postMe);
        if(postMe.getType() == null){
            throw new MessageMustHaveTypeException();
        }

        if(postMe.getContent() == null || postMe.getContent().length() == 0){
            throw new MessageMustHaveContentException();
        }

        Message msg = new Message();
        msg.setCreationDate(new Date());
        msg.setContent(postMe.getContent());
        msg.setType(postMe.getType());
        return messageRepository.save(msg);
    }
}

