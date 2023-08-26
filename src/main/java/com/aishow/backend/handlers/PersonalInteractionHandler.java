package com.aishow.backend.handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.aishow.backend.managers.DatabaseConnection;
import com.aishow.backend.managers.UserConnectionManager;
import com.aishow.backend.managers.Utils;

public class PersonalInteractionHandler implements BaseHandler {
    @Override
    public <T, G> G handle(T reqBody){
        return null;
        // if (UserConnectionManager.hasIp(exchange.getRemoteAddress().getHostString())) {
        //     var params = Utils.queryToMap(exchange.getRequestURI().getQuery());
            
        //     //É refresh
        //     if (params == null){
        //         new InitHandler().sendInitialContent("/", exchange);
        //         return;
        //     }

        //     switch (params.get("type")) {
        //         case "reload":
        //             exchange.getResponseHeaders().add("Content-type", "application/json");
        //             var toSend = UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).toJson().getBytes(StandardCharsets.UTF_8);
        //             Utils.sendAndClose(exchange,200,toSend);
        //             break;
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
        //         case "imageUpdate":
        //             if (Utils.updateUserProfilePicture(exchange.getRemoteAddress().getHostString(),"src/raw/images/" + UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).getUserId(),"png", exchange.getRequestBody().readAllBytes())) {
        //                 exchange.getResponseHeaders().add("Content-type", "text/plain");
        //                 Utils.sendAndClose(exchange,201, "".getBytes(StandardCharsets.UTF_8));
        //             } else {
        //                 exchange.getResponseHeaders().add("Content-type", "text/plain");
        //                 Utils.sendAndClose(exchange,500, "".getBytes(StandardCharsets.UTF_8));
        //             }
        //             break;
        //         case "nameUpdate":
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
}
