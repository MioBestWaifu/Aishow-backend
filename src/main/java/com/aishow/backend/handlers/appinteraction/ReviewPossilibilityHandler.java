package com.aishow.backend.handlers.appinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;

public class ReviewPossilibilityHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        try {
        if (params[0].equals("services")) {
            PreparedStatement st = StatementPreparer.checkIfUserHasUsedService(DatabaseConnection.getConnection(), Integer.parseInt(params[1]), Integer.parseInt(params[2]));
            ResultSet rs = DatabaseConnection.runQuery(st);
            if (rs.next())
                return (G) "OK";
            return (G) "NO";
        } else {
            PreparedStatement st = StatementPreparer.checkIfUserHasUsedServiceWithProvider(DatabaseConnection.getConnection(), Integer.parseInt(params[1]), Integer.parseInt(params[2]));
            ResultSet rs = DatabaseConnection.runQuery(st);
            if (rs.next())
                return (G) "OK";
            return (G) "NO";
        }
        } catch (Exception e) {
            e.printStackTrace();
            return (G) "NO";
        }
    }
    
}
