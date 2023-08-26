package com.aishow.backend.handlers.serviceinteraction;

import com.aishow.backend.handlers.BaseHandler;

public class ScheduleServiceHandler extends BaseHandler{

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
    
    //case "schedule":
        //             ClientServiceInteraction csInfo = new ClientServiceInteraction(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
        //             csInfo.setClientId(UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).getUserId());
        //             if (validateRequest(csInfo, UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()))){
        //                 DatabaseConnection.addNewServiceRequest(csInfo);
        //                 exchange.getResponseHeaders().add("Content-type", "text/plain");
        //                 Utils.sendAndClose(exchange,201,"".getBytes());
        //             } else {
        //                 exchange.getResponseHeaders().add("Content-type", "text/plain");
        //                 System.out.println("Faio");
        //                 Utils.sendAndClose(exchange,403,"".getBytes());
        //             }
        //             break;
}
