package com.aishow.backend.handlers;

public interface BaseHandler {
    
    public abstract <T, G> G handle(T reqBody);
}
