package com.te2.controller;

import com.te2.data.Message;
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
    public Message create(@RequestBody Message postMe) {
        log.debug("this is the start of the method!");
        postMe.setCreationDate(new Date());
        if(postMe.getType() == null){
            throw new MessageMustHaveTypeException();
        }
        return messageRepository.save(postMe);
    }
}

