package com.nnk.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistException extends RuntimeException {
    /**
     * Exception to throw when try to update or save
     * user with existing username.
     * @param message .
     */
    public UsernameAlreadyExistException(final String message) {
        super(message);
    }
}
