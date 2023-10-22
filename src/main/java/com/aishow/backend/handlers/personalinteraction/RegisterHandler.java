package com.aishow.backend.handlers.personalinteraction;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.UserInformation;
import com.aishow.backend.utils.Utils;

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
