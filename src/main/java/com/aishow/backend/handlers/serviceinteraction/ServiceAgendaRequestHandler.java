package com.aishow.backend.handlers.serviceinteraction;

import com.aishow.backend.handlers.BaseHandler;

public class ServiceAgendaRequestHandler extends BaseHandler{

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

    //         case "requestSchedule":
        //             exchange.getResponseHeaders().add("Content-type", "application/json");
        //             Utils.sendAndClose(exchange, 200, DatabaseConnection.getScheduleByProvider(UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).getUserId()).toJson().getBytes(StandardCharsets.UTF_8));
        //             break;
    
}
