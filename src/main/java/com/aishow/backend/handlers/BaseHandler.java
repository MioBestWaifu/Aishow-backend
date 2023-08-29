package com.aishow.backend.handlers;

import java.io.IOException;

public abstract class BaseHandler {
    
    public abstract <T, G> G handle(T reqBody);
    public abstract <T, G> G handle(T reqBody, String[] params);
}
