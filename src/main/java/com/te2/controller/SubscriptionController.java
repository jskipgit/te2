package com.te2.controller;

import com.te2.data.Message;
import com.te2.data.Subscription;
import com.te2.dto.SubscriptionRead;
import com.te2.exception.SubscriptionNotFoundException;
import com.te2.repo.MessageRepository;
import com.te2.repo.SubscriptionRepository;
import com.te2.repo.SubscriptionRepositoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jasonskipper on 4/17/17.
 */
@RestController
@RequestMapping(path = "/te2/subscriptions")
public class SubscriptionController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MessageRepository messageRepository;


    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Iterable<Subscription> list(){
        return subscriptionRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Subscription create(@RequestBody Subscription postMe) {
        log.debug("Somebody just made a new subscription to:"+postMe.getSubs());
        return subscriptionRepository.save(postMe);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Subscription update(@RequestBody  Subscription postMe) {
        Subscription fromDb = subscriptionRepository.findOne(postMe.getId());
        if(fromDb != null){
            fromDb.setSubs(postMe.getSubs());
            subscriptionRepository.save(fromDb);
            log.debug("Somebody just made an update to:"+fromDb.getId());
        } else {
            log.error("Could not find subscription by ID:"+postMe.getId());
            throw new SubscriptionNotFoundException();
        }

        return fromDb;
    }

    @RequestMapping(method = RequestMethod.GET)
    public SubscriptionRead read(@RequestParam Long subscriptionId,
                                 @RequestParam Integer page,
                                 @RequestParam Integer size,
                                 @RequestParam(required = false) String sortby,
                                 @RequestParam(required = false) Sort.Direction direction){
        Subscription sub = subscriptionRepository.findOne(subscriptionId);

        if(sub != null) {
            if (sortby == null) {
                sortby = "creationDate";
            }

            if (direction == null) {
                direction = Sort.Direction.ASC;
            }
            Sort s = new Sort(direction, sortby);
            PageRequest pr = new PageRequest(page, size, s);
            Page<Message> messsages = messageRepository.fetchMessagesByType(sub.getSubs(), pr);
            List<Object[]> stats = subscriptionRepository.getSubscriptionStats();

            // pack response object
            SubscriptionRead subscriptionRead = new SubscriptionRead();
            subscriptionRead.setMessages(messsages);
            subscriptionRead.setStats(SubscriptionRepositoryUtil.convert(stats, sub));
            return subscriptionRead;
        }else{
            throw new SubscriptionNotFoundException();
        }
    }

}
