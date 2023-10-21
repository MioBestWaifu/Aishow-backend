package com.aishow.backend.handlers.personalinteraction;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.managers.DatabaseConnection;
import com.aishow.backend.managers.Utils;
import com.aishow.backend.models.UserInformation;

public class RegisterHandler extends BaseHandler{

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

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO Auto-generated method stub
        return null;
    }

    
}
