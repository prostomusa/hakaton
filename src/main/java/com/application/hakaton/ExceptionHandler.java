package com.application.hakaton;

import com.application.hakaton.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleAny(Exception e) {
        logger.error(e.getMessage(), e);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({
            NotFoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleClientException(Exception e) {
        logger.error(e.getMessage(), e);
    }
}
