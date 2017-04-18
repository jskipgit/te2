package com.te2.controller;

import com.te2.data.Message;
import com.te2.data.Subscription;
import com.te2.dto.DTOSubscription;
import com.te2.dto.SubscriptionRead;
import com.te2.exception.SubscriptionNotFoundException;
import com.te2.repo.MessageRepository;
import com.te2.repo.SubscriptionRepository;
import com.te2.repo.SubscriptionRepositoryUtil;
import io.swagger.annotations.ApiParam;
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
    public Subscription create(@RequestBody DTOSubscription postMe) {
        log.debug("Somebody just made a new subscription to:"+postMe.getTypes());
        Subscription newSub = new Subscription();
        newSub.setName(postMe.getName());
        newSub.setTypes(postMe.getTypes());
        return subscriptionRepository.save(newSub);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Subscription update(@RequestBody  Subscription postMe) {
        Subscription fromDb = subscriptionRepository.findOne(postMe.getId());
        if(fromDb != null){
            fromDb.setTypes(postMe.getTypes());
            subscriptionRepository.save(fromDb);
            log.debug("Somebody just made an update to:"+fromDb.getId());
        } else {
            log.error("Could not find subscription by ID:"+postMe.getId());
            throw new SubscriptionNotFoundException();
        }

        return fromDb;
    }

    @RequestMapping(method = RequestMethod.GET)
    public SubscriptionRead read(@ApiParam(value = "This is the id field found in the Subscription object.  You can obtain this by calling either LIST (all) Subscriptions or in the response to a CREATE Subscription.")
                                 @RequestParam Long subscriptionId,
                                 @ApiParam(value = "Specify which page of data you want, this is zero-based.")
                                 @RequestParam Integer page,
                                 @ApiParam(value = "Specify the size of a page of data.")
                                     @RequestParam Integer size,
                                 @ApiParam(value = "Specify which attribute of message should be used to sort.  Valid options are: creationDate (default), id, content, and type.")
                                     @RequestParam(required = false) String sortby,
                                 @ApiParam(value = "Specify sort direction.  Default is DESC.")
                                     @RequestParam(required = false) Sort.Direction direction){
        Subscription sub = subscriptionRepository.findOne(subscriptionId);

        if(sub != null) {
            if (sortby == null) {
                sortby = "creationDate";
            }

            if (direction == null) {
                direction = Sort.Direction.DESC;
            }
            Sort s = new Sort(direction, sortby);
            PageRequest pr = new PageRequest(page, size, s);
            Page<Message> messsages = messageRepository.fetchMessagesByType(sub.getTypes(), pr);
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
