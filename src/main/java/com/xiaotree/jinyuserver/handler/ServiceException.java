package com.xiaotree.jinyuserver.handler;

public class ServiceException extends RuntimeException{
    public ServiceException(String message) {
        super(message);
    }
}
