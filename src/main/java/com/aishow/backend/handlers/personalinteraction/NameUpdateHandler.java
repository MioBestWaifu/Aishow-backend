package com.aishow.backend.handlers.personalinteraction;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;

public class NameUpdateHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }


    //Parar de receber o user como parametro como AUTH
    /**
     * @param reqBody texto do novo nome
     * @param params idUser no 0
     */
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        try {
            PreparedStatement st = StatementPreparer.updateUserName(DatabaseConnection.getConnection(), 
            Integer.parseInt(params[0]), (String) reqBody);
            int x = DatabaseConnection.runUpdate(st);
            if (x == 1)
                return (G) "OK";
            return (G) "FAIL";
        } catch (SQLException ex){
            ex.printStackTrace();
            return (G) "FAIL";
        }
    }
}
