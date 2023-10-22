package com.aishow.backend.handlers.serviceinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.ClientServiceInteraction;
import com.mysql.cj.xdevapi.PreparableStatement;

public class ScheduleServiceHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        //Isso precisa de validação
        //Diferencia os erros
        try{
            var y = (ClientServiceInteraction) reqBody;
            var x = checkAvailability(y);
            if (!x.equals("AVA"))
                return (G)x;
            if(addNewRequest(y))
                return (G)"OK";
            else 
                return (G)"FAIL";
        } catch(Exception ex){
            return (G)"FAIL";
        }
    }
    

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        return null;
    }

    public String checkAvailability(ClientServiceInteraction info){
        try {
            PreparedStatement st = StatementPreparer.getAvailabilityAtFrame(DatabaseConnection.getConnection(), info);
            ResultSet rs = DatabaseConnection.runQuery(st);
            if(rs.next()){
                String toReturn = "Unavailable that day between: \n"+rs.getTime("startTime").toString() + 
                    " and "+rs.getTime("endTime").toString();
                while (rs.next()){
                    toReturn += "\n"+rs.getTime("startTime").toString() + 
                    " and "+rs.getTime("endTime").toString();
                }
                return toReturn;
            } else {
                return "AVA";
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }

    public boolean addNewRequest (ClientServiceInteraction info) {
        try {
            PreparedStatement st = StatementPreparer.createServiceRequest(DatabaseConnection.getConnection(), info);
            return DatabaseConnection.runUpdate(st) == 1;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
