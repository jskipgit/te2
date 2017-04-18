package com.te2.repo;

import com.te2.data.MessageType;
import com.te2.data.Subscription;
import com.te2.dto.SubscriptionStats;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jasonskipper on 4/17/17.
 */
public class SubscriptionRepositoryUtil {

    /**
     * This is a utility method for taking a list of results from a generic list of objects to a filtered
     * collection of Subscription Stats pertaining to all the DTOMessage Types found within those of the supplied subscption.
     * @param results
     * @param s
     * @return
     */
    public static Collection<SubscriptionStats> convert(List<Object[]> results, Subscription s){
        return results.stream()
                .filter(type -> s.getTypes().contains(MessageType.valueOf(type[0].toString())))
                .map(type -> new SubscriptionStats(MessageType.valueOf(type[0].toString()),
                        Long.parseLong(type[1].toString())))
                .collect(Collectors.toList());
    }
}
