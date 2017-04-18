package com.te2.repo;

import com.te2.data.Message;
import com.te2.data.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by jasonskipper on 4/16/17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application.test.properties")
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void saveMessage() throws Exception {
        // save one
        Message m = new Message();
        m.setContent("Hello World");
        m.setType(MessageType.MOVIE_RELEASE);
        m.setCreationDate(Calendar.getInstance().getTime());
        m = messageRepository.save(m);

        // fetch
        Message foundFromDb = messageRepository.findOne(m.getId());

        //validate
        assertNotNull("Simple save and fetch failed", foundFromDb);
        assertEquals("DTOMessage content mis-match",m.getContent(),foundFromDb.getContent());
        assertEquals("DTOMessage creation date mis-match",m.getCreationDate(),foundFromDb.getCreationDate());
    }

}