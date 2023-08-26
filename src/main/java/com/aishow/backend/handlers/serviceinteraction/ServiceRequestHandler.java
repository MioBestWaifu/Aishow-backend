package com.aishow.backend.handlers.serviceinteraction;

import com.aishow.backend.handlers.BaseHandler;

public class ServiceRequestHandler extends BaseHandler{

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

    
    //         case "request":
        //             exchange.getResponseHeaders().add("Content-type", "application/json");
        //             Utils.sendAndClose(exchange,200, DatabaseConnection.getFullServiceInformation(Integer.parseInt(params.get("id")),true).toJson().getBytes(StandardCharsets.UTF_8));
        //             break;
}
