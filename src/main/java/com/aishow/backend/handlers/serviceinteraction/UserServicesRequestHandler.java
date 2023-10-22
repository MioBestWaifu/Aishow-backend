package com.aishow.backend.handlers.serviceinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.ServiceInformation;

public class UserServicesRequestHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Passar esse metodo pro normal com o AUTH, passar o id vai ser desnecessario
     * @param params passar id no 0
     */
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        try {
            ArrayList<ServiceInformation> toReturn = new ArrayList<>();
            PreparedStatement st = StatementPreparer.getServicesByProviderId(DatabaseConnection.getConnection(), Integer.parseInt(params[0]));
            ResultSet rs = DatabaseConnection.runQuery(st);
            while (rs.next()){
                toReturn.add(ServiceInformation.fromResultSet(rs));
            }

            for (ServiceInformation serviceInformation : toReturn) {
                ServiceRequestHandler.completeServiceInformaiton(serviceInformation);
            }

            return (G) toReturn;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
