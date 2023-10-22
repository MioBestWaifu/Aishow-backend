package com.aishow.backend.handlers.personalinteraction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.managers.DatabaseConnection;
import com.aishow.backend.managers.UserConnectionManager;
import com.aishow.backend.models.UserInformation;
import com.aishow.backend.utils.Utils;

public class LoginHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody){
        UserInformation info = (UserInformation) reqBody;
        if (DatabaseConnection.checkLogin(info)){
            info = DatabaseConnection.getActiveUserInformation(info);
            //UserConnectionManager.setConnectionUserInfo(exchange.getRemoteAddress().getHostString(), info);
            return (G) info;
        } else {
            var errorInformation = new UserInformation();
            errorInformation.setEmail("NULL");
            return (G) errorInformation;
        }
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO Auto-generated method stub
        return null;
    }

    
    
}
