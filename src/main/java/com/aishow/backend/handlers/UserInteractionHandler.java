package com.aishow.backend.handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.aishow.backend.info.ClientServiceInteraction;
import com.aishow.backend.info.UserInformation;
import com.aishow.backend.managers.DatabaseConnection;
import com.aishow.backend.managers.UserConnectionManager;
import com.aishow.backend.managers.Utils;

public class UserInteractionHandler implements BaseHandler{

    @Override
    public <T, G> G handle(T reqBody){
        return null;
        // if (UserConnectionManager.hasIp(exchange.getRemoteAddress().getHostString())){
        //     var params = Utils.queryToMap(exchange.getRequestURI().getQuery());
            
        //     //É refresh
        //     if (params == null){
        //         new InitHandler().sendInitialContent("/", exchange);
        //         return;
        //     }

        //     switch (params.get("type")){
        //         case "request":
        //             exchange.getResponseHeaders().add("Content-type", "application/json");
        //             Utils.sendAndClose(exchange,200, DatabaseConnection.getRequestedUserInformation(Integer.parseInt(params.get("id"))).toJson().getBytes(StandardCharsets.UTF_8));
        //             break;
        //         case "chat":
        //         case "review":
        //             break;
        //         default:
        //             break;
        //     }
        // } else {
        //     UserConnectionManager.addConnection(exchange.getRemoteAddress().getHostString());
        //     UserConnectionManager.getConnection(exchange.getRemoteAddress().getHostString()).lastPage = "/pages/login";
        //     new InitHandler().sendInitialContent("/", exchange);
        //     return;
        // }
    }

    public boolean validateRequest(ClientServiceInteraction info, UserInformation client){
        if (info.getCost() <= DatabaseConnection.getCredits(client.getUserId())){
            //CHECAR SE O CLIENTE JA TEM UM REQUEST OU INSTANCE PARA AQUELA HORA
            return true;
        } else{
            return false;
        }
    }
    
}
