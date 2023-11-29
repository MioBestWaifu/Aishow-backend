package com.aishow.backend.handlers.personalinteraction;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.GeoLimitation;

public class GeoLimitationUpdateHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        try {
            GeoLimitation[] buffer = (GeoLimitation[]) reqBody;
            ArrayList<GeoLimitation> geoLimitations = new ArrayList<>(Arrays.asList(buffer));
            PreparedStatement st = StatementPreparer.updateGeoLimitations(DatabaseConnection.getConnection(), geoLimitations);
            System.out.println(DatabaseConnection.runBatchUpdate(st));
            return (G) "OK";
        } catch (Exception ex){
            ex.printStackTrace();
            return (G) "NO";
        }
        
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }
    
}
