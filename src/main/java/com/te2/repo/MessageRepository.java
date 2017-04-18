package com.te2.repo;

import com.te2.data.Message;
import com.te2.data.MessageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by jasonskipper on 4/17/17.
 */
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.type IN (?1)")
    Page<Message> fetchMessagesByType(List<MessageType> types, Pageable pageable);

}
