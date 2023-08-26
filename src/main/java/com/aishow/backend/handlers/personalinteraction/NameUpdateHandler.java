package com.aishow.backend.handlers.personalinteraction;

import com.aishow.backend.handlers.BaseHandler;

public class NameUpdateHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO Auto-generated method stub
        return null;
    }

    
    
    //case "nameUpdate":
        //             String newName = new String(exchange.getRequestBody().readAllBytes());
        //             if (DatabaseConnection.tryToUpdateUserName(UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).getUserId(),newName)) {
        //                 UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).setName(newName);
        //                 exchange.getResponseHeaders().add("Content-type", "text/plain");
        //                 Utils.sendAndClose(exchange,201,"".getBytes(StandardCharsets.UTF_8));
        //             } else {
        //                 exchange.getResponseHeaders().add("Content-type", "text/plain");
        //                 Utils.sendAndClose(exchange,500,"FAIL".getBytes(StandardCharsets.UTF_8));
        //             }
        //             break;
}
