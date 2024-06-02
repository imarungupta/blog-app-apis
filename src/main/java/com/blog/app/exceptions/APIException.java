package com.blog.app.exceptions;

import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

public class APIException extends RuntimeException{

    public APIException(String message) {
        super(message);
    }

    public APIException() {
    }
}
