package com.te2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jasonskipper on 4/18/17.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MessageMustHaveContentException extends RuntimeException {

}
