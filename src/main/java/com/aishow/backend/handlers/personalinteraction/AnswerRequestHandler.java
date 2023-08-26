package com.aishow.backend.handlers.personalinteraction;

import com.aishow.backend.handlers.BaseHandler;

public class AnswerRequestHandler extends BaseHandler{

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

    

    //         case "accept":
        //             if(DatabaseConnection.AcceptRequest(Integer.parseInt(params.get("id")),UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).getUserId())){
        //                 Utils.sendAndClose(exchange, 201, "".getBytes());
        //             } else {
        //                 Utils.sendAndClose(exchange, 400, "".getBytes());
        //             }
        //             break;
    //         case "deny":
        //             if(DatabaseConnection.DenyRequest(Integer.parseInt(params.get("id")),UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).getUserId())){
        //                 Utils.sendAndClose(exchange, 201, "".getBytes());
        //             } else {
        //                 Utils.sendAndClose(exchange, 400, "".getBytes());
        //             }
        //             break;
    
}
