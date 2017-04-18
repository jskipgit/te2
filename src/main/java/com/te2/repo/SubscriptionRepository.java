package com.te2.repo;

import com.te2.data.Subscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;


/**
 * Created by jasonskipper on 4/17/17.
 */
public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, Long> {

       @Query(value = "select  subs, count(*) from subscription_subs  GROUP BY subs", nativeQuery = true)
       List<Object[]> getSubscriptionStats();
}
