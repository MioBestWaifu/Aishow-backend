package com.aishow.backend.handlers.personalinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.UserInformation;

public class ReloadUserHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        return null;
    }

    //AUTH
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        try{
            PreparedStatement st = StatementPreparer.getUserById(DatabaseConnection.getConnection(),Integer.parseInt(params[0]));
            ResultSet rs = DatabaseConnection.runQuery(st);
            rs.next();
            return (G) UserInformation.fromResultSet(rs);
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
}
