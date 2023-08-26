package com.aishow.backend.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.aishow.backend.info.UserInformation;
import com.aishow.backend.managers.DatabaseConnection;
import com.aishow.backend.managers.Utils;

public class RegisterHandler implements BaseHandler{

    @Override
    public <T, G> G handle(T reqBody){
        // String x = new String(exchange.getRequestBody().readAllBytes());
        if (DatabaseConnection.tryToRegister((UserInformation)reqBody)){
            return (G)"OK";
        } else{
        //     exchange.getResponseHeaders().add("Content-type", "text/plain");
        return (G)"NO";
        }
    }

    
}
