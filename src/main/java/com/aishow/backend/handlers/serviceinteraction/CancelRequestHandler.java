package com.aishow.backend.handlers.serviceinteraction;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.handlers.BaseHandler;

public class CancelRequestHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        if (DatabaseConnection.cancelRequest(Integer.parseInt(params[0]))){
            return (G)"OK";
        } 
        return (G) "FAIL";
    }
    
}
