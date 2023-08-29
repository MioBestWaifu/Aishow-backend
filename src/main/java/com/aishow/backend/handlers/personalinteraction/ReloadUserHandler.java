package com.aishow.backend.handlers.personalinteraction;

import com.aishow.backend.handlers.BaseHandler;

public class ReloadUserHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO Auto-generated method stub
        return null;
    }

    

    //         case "reload":
        //             exchange.getResponseHeaders().add("Content-type", "application/json");
        //             var toSend = UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).toJson().getBytes(StandardCharsets.UTF_8);
        //             Utils.sendAndClose(exchange,200,toSend);
        //             break;
    
}
