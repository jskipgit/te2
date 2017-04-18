package com.te2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jasonskipper on 4/17/17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SubscriptionNotFoundException extends RuntimeException {

}
