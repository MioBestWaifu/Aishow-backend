package com.aishow.backend.handlers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.aishow.backend.managers.Utils;

public class InitHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody){
        return null;
        // String path = exchange.getRequestURI().toString();
        // byte[] toSend;
        // sendInitialContent(path, exchange);
    }

    public void sendInitialContent(String path, HttpExchange exchange){
        
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO Auto-generated method stub
        return null;
    }


    
}