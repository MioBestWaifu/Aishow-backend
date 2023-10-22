package com.aishow.backend.handlers.serviceinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.ClientServiceInteraction;
import com.aishow.backend.models.ServiceInformation;

public class UserMadeServiceRequestsHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        try{
            ArrayList<ClientServiceInteraction> toReturn = new ArrayList<>();
            PreparedStatement st = StatementPreparer.getRequestsByClientId(DatabaseConnection.getConnection(), Integer.parseInt(params[0]));
            ResultSet rs = DatabaseConnection.runQuery(st);
            while (rs.next()){
                ClientServiceInteraction buffer = ClientServiceInteraction.fromResultSet(rs, false);
                st = StatementPreparer.getServiceById(DatabaseConnection.getConnection(), buffer.getTemplateId());
                ResultSet servRs = DatabaseConnection.runQuery(st);
                buffer.setService(ServiceInformation.fromResultSet(servRs));
                toReturn.add(buffer);
            }

            return (G) toReturn;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
}
