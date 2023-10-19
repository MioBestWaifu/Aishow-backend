package com.aishow.backend.handlers.appinteraction;

import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.managers.DatabaseConnection;

public class SearchHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        if (params[0].equals("service")){
            return(G) DatabaseConnection.searchForServiceTemplate(params[1], Integer.parseInt(params[2]));
        } else if (params[0].equals("users")){
            return(G) DatabaseConnection.searchForUsers(params[1], Integer.parseInt(params[2]));
        } else {
            return null;
        }
    }
    
}
