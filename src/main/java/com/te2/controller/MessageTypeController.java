package com.te2.controller;

import com.te2.data.MessageType;
import com.te2.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * Created by jasonskipper on 4/17/17.
 */
@RestController
@RequestMapping(path = "/te2/")
public class MessageTypeController {

    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping(value = "messageTypes", method = RequestMethod.GET)
    public List<MessageType> list(){
        return Arrays.asList(MessageType.values());
    }

}

