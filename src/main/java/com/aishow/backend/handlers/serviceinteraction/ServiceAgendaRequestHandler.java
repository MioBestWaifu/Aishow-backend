package com.aishow.backend.handlers.serviceinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.handlers.userinteraction.UserRequestHandler;
import com.aishow.backend.models.ClientServiceInteraction;
import com.aishow.backend.models.ServiceSchedule;
import com.mysql.cj.xdevapi.PreparableStatement;

//TODO #33 separar completamente requisições feitas pelo usuoário de feitas ao usuário

public class ServiceAgendaRequestHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        return null;
    }

    //Verificacao de ID com o AUTH
    //AUTH SECURITY
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        try {
            ServiceSchedule toReturn = new ServiceSchedule();
            PreparedStatement st = StatementPreparer.getNonCompletedInstancesByProviderId(DatabaseConnection.getConnection(), Integer.parseInt(params[0]));
            ResultSet rs = DatabaseConnection.runQuery(st);
            ArrayList<ClientServiceInteraction> instances = new ArrayList<>();
            while (rs.next()){
                ClientServiceInteraction buffer = ClientServiceInteraction.fromResultSet(rs, true);
                buffer.setService(new ServiceRequestHandler().handle(null, new String[]{Integer.toString(buffer.getService().getTemplateId())}));
                buffer.setClient(new UserRequestHandler().handle(null, new String[]{Integer.toString(buffer.getClient().getUserId())}));;
                buffer.setProvider(true);
                instances.add(buffer);
            }

            st = StatementPreparer.getNonCompletedInstancesByClientId(DatabaseConnection.getConnection(), Integer.parseInt(params[0]));
            rs = DatabaseConnection.runQuery(st);

            while (rs.next()){
                ClientServiceInteraction buffer = ClientServiceInteraction.fromResultSet(rs, true);
                buffer.setService(new ServiceRequestHandler().handle(null, new String[]{Integer.toString(buffer.getService().getTemplateId())}));
                buffer.setProvider(false);
                instances.add(buffer);
            }

            toReturn.setPendingInstances(instances);
            st = (StatementPreparer.getRequestsByProviderId(DatabaseConnection.getConnection(), Integer.parseInt(params[0])));
            rs = DatabaseConnection.runQuery(st);

            ArrayList<ClientServiceInteraction> requests = new ArrayList<>();
            while (rs.next()){
                ClientServiceInteraction buffer = ClientServiceInteraction.fromResultSet(rs, false);
                buffer.setService(new ServiceRequestHandler().handle(null, new String[]{Integer.toString(buffer.getService().getTemplateId())}));
                buffer.setClient(new UserRequestHandler().getUserById(buffer.getClient().getUserId()));
                buffer.setProvider(true);
                requests.add(buffer);
            }

            toReturn.setPendingRequests(requests);
            return (G) toReturn;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }  
}
