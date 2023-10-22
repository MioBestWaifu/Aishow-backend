package com.aishow.backend.handlers.personalinteraction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.managers.UserConnectionManager;
import com.aishow.backend.models.UserInformation;
import com.aishow.backend.utils.Utils;

public class LoginHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody){
        UserInformation info = (UserInformation) reqBody;
        try {
            var st = StatementPreparer.matchUserCredentials(DatabaseConnection.getConnection(), info);
            var rs = DatabaseConnection.runQuery(st);
            if (rs.next()){
                return (G) UserInformation.fromResultSet(rs);
            } else {
                info.setEmail("WRONG");
                return (G) info;
            }
        } catch (SQLException ex){
            info.setEmail("ERROR");
            return (G) info;
        }
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO Auto-generated method stub
        return null;
    }

    
    
}
