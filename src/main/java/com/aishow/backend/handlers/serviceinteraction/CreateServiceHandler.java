package com.aishow.backend.handlers.serviceinteraction;

import com.aishow.backend.handlers.BaseHandler;

public class CreateServiceHandler extends BaseHandler{

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

    //         case "create":
        //             var x = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        //             ServiceInformation servInfo = new ServiceInformation(x);
        //             servInfo.setProviderId(UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).getUserId());
        //             if(DatabaseConnection.tryToAddServiceTemplate(servInfo)){
        //                 exchange.getResponseHeaders().add("Content-type", "text/plain");
        //                 Utils.sendAndClose(exchange,201,"".getBytes());
        //             } else {
        //                 exchange.getResponseHeaders().add("Content-type", "text/plain");
        //                 Utils.sendAndClose(exchange,500,"".getBytes());
        //             }
        //             break;
}
