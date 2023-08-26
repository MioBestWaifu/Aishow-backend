package com.aishow.backend.handlers.userinteraction;

import com.aishow.backend.handlers.BaseHandler;

public class UserRequestHandler extends BaseHandler{
    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }
    
    //         case "request":
        //             exchange.getResponseHeaders().add("Content-type", "application/json");
        //             Utils.sendAndClose(exchange,200, DatabaseConnection.getRequestedUserInformation(Integer.parseInt(params.get("id"))).toJson().getBytes(StandardCharsets.UTF_8));
        //             break;
}
