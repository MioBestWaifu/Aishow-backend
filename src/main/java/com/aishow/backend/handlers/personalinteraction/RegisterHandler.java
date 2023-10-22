package com.aishow.backend.handlers.personalinteraction;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.jna.platform.win32.Netapi32Util.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.UserInformation;
import com.aishow.backend.utils.Utils;

public class RegisterHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody){
        try {
            UserInformation info = (UserInformation) reqBody;
            PreparedStatement st = StatementPreparer.getUserByEmail(DatabaseConnection.getConnection(), info.getEmail());
            ResultSet rs = DatabaseConnection.runQuery(st);
            if (!rs.next()){
                st = StatementPreparer.createUser(DatabaseConnection.getConnection(), info);
                int x = DatabaseConnection.runUpdate(st);
                if (x == 1)
                    return (G) "OK";
                return (G) "NO";
            } else{
                return (G)"NO";
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            return (G) "NO";
        }
    } 

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO Auto-generated method stub
        return null;
    }


}
