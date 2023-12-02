package com.aishow.backend.handlers.personalinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.GeoLimitation;
import java.util.ArrayList;

public class GeoLimitationsRequestHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        try{
            PreparedStatement st = StatementPreparer.getGeoLimitationsByUserId(DatabaseConnection.getConnection(), Integer.parseInt(params[0]));
            ResultSet rs = DatabaseConnection.runQuery(st);
            ArrayList<GeoLimitation> toReturn = new ArrayList<>();
            try {
                while (rs.next()) {
                    toReturn.add(GeoLimitation.fromResultSet(rs));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return (G) toReturn;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }   
    }
}