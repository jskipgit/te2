package com.te2.repo;

import com.te2.data.Message;
import com.te2.data.MessageType;
import com.te2.data.Subscription;
import com.te2.dto.SubscriptionStats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jasonskipper on 4/17/17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application.test.properties")
public class SubscriptionRepositoryTest {

    private static final String TEST_CONTENT_NEWS = "Hello World News";
    private static final String TEST_CONTENT_MOVIES = "Hello World Movies";

    @Autowired
    private  MessageRepository messageRepository;

    @Autowired
    private  SubscriptionRepository subscriptionRepository;

    @Test
    public void getMessagesBySubscriptions() throws Exception {

        // Save TWO messages
        Message m = new Message();
        m.setContent(TEST_CONTENT_MOVIES);
        m.setType(MessageType.MOVIE_RELEASE);
        m.setCreationDate(Calendar.getInstance().getTime());
        m = messageRepository.save(m);

        m = new Message();
        m.setContent(TEST_CONTENT_NEWS);
        m.setType(MessageType.NEWS);
        m.setCreationDate(Calendar.getInstance().getTime());
        m = messageRepository.save(m);

        // save a subscription with one message type
        Subscription s = new Subscription();
        s.setTypes(Arrays.asList(MessageType.NEWS));
        subscriptionRepository.save(s);

        //fetch
        Subscription fromDb = subscriptionRepository.findOne(s.getId());
        PageRequest p = new PageRequest(0, 10);
        Page<Message> results = messageRepository.fetchMessagesByType(fromDb.getTypes(), p);

        // validate
        assertNotNull("Simple save and fetch failed", results);
        assertEquals("Count should match",1, results.getTotalElements());
        Message fromSub = results.getContent().get(0);
        assertEquals("Simple save and fetch failed", fromSub.getContent(), TEST_CONTENT_NEWS );

    }


    @Test
    public void getMessagesBySubscriptionStats() throws Exception {

        // save a subscription with one message type
        Subscription s = new Subscription();
        s.setTypes(Arrays.asList(MessageType.MOVIE_RELEASE));
        subscriptionRepository.save(s);

        s = new Subscription();
        s.setTypes(Arrays.asList(MessageType.MOVIE_RELEASE));
        subscriptionRepository.save(s);

        s = new Subscription();
        s.setTypes(Arrays.asList(MessageType.MOVIE_RELEASE));
        subscriptionRepository.save(s);

        s = new Subscription();
        s.setTypes(Arrays.asList(MessageType.MOVIE_RELEASE, MessageType.NEW_HIRES));
        subscriptionRepository.save(s);

        s = new Subscription();
        s.setTypes(Arrays.asList(MessageType.NEW_HIRES));
        subscriptionRepository.save(s);

        s = new Subscription();
        s.setTypes(Arrays.asList(MessageType.TRANSIT_UPDATES));
        subscriptionRepository.save(s);

        s = new Subscription();
        s.setTypes(Arrays.asList(MessageType.NEW_HIRES, MessageType.MOVIE_RELEASE, MessageType.PRIORITY));
        subscriptionRepository.save(s);

        //fetch
        Subscription fromDb = subscriptionRepository.findOne(s.getId());
        PageRequest p = new PageRequest(0, 10);
        Page<Message> results = messageRepository.fetchMessagesByType(fromDb.getTypes(), p);

        // validate
        List<Object[]> stats = subscriptionRepository.getSubscriptionStats();
        assertNotNull("Stats 404", stats);


        Collection<SubscriptionStats> finalStats = SubscriptionRepositoryUtil
                .convert(stats, s);

        assertNotNull("Stats 404", finalStats);
        assertEquals("Incorrect stat count", fromDb.getTypes().size(), finalStats.size());
        assertEquals("Incorrect stat for Movies", 5, finalStats.stream().filter( stat -> stat.getType().equals(MessageType.MOVIE_RELEASE))
                .mapToLong(stat -> stat.getReadCount())
                .sum());
        assertEquals("Incorrect stat for Priority", 1, finalStats.stream().filter( stat -> stat.getType().equals(MessageType.PRIORITY))
                .mapToLong(stat -> stat.getReadCount())
                .sum());
        assertEquals("Incorrect stat for New Hires", 3, finalStats.stream().filter( stat -> stat.getType().equals(MessageType.NEW_HIRES))
                .mapToLong(stat -> stat.getReadCount())
                .sum());

    }
}