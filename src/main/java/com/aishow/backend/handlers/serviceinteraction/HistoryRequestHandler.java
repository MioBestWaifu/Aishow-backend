package com.aishow.backend.handlers.serviceinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.handlers.userinteraction.UserRequestHandler;
import com.aishow.backend.models.ClientServiceInteraction;
import com.aishow.backend.models.ServiceSchedule;
import java.util.ArrayList;

public class HistoryRequestHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
         try {
            ArrayList<ClientServiceInteraction> toReturn = new ArrayList<>();
            PreparedStatement st = StatementPreparer.getCompletedInstancesByProviderId(DatabaseConnection.getConnection(), Integer.parseInt(params[0]));
            ResultSet rs = DatabaseConnection.runQuery(st);
            while (rs.next()){
                ClientServiceInteraction buffer = ClientServiceInteraction.fromResultSet(rs, true);
                buffer.setService(new ServiceRequestHandler().handle(null, new String[]{Integer.toString(buffer.getService().getTemplateId())}));
                buffer.setClient(new UserRequestHandler().handle(null, new String[]{Integer.toString(buffer.getClient().getUserId())}));;
                buffer.setProvider(true);
                toReturn.add(buffer);
            }

            st = StatementPreparer.getCompletedInstancesByClientId(DatabaseConnection.getConnection(), Integer.parseInt(params[0]));
            rs = DatabaseConnection.runQuery(st);

            while (rs.next()){
                ClientServiceInteraction buffer = ClientServiceInteraction.fromResultSet(rs, true);
                buffer.setService(new ServiceRequestHandler().handle(null, new String[]{Integer.toString(buffer.getService().getTemplateId())}));
                buffer.setProvider(false);
                toReturn.add(buffer);
            }
            return (G) toReturn;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
}
