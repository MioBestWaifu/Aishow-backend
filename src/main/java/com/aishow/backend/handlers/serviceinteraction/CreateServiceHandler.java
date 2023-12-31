package com.aishow.backend.handlers.serviceinteraction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.ServiceInformation;
import com.aishow.backend.models.UserInformation;

public class CreateServiceHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        return null;
    }

    /**
     * Passar esse metodo pro normal com o AUTH, passar o id vai ser desnecessario
     * @param params passar id no 0
     */
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        try {
            ServiceInformation x = (ServiceInformation) reqBody;
            x.setProvider(new UserInformation());
            x.getProvider().setUserId(Integer.parseInt(params[0]));
            PreparedStatement st = StatementPreparer.createService(DatabaseConnection.getConnection(), x);
            int y = DatabaseConnection.runUpdate(st);

            st = StatementPreparer.getLastCreatedServiceByProviderId(DatabaseConnection.getConnection(), x.getProvider().getUserId());
            ResultSet rs = DatabaseConnection.runQuery(st);
            rs.next();
            x.setTemplateId(rs.getInt(1));
            if(y != 1)
                return (G) "FAIL BASIC";
            
            if(!new UpdateServiceHandler().updateAvailability(x))
                return (G) "FAIL AVAILABILITY";
            
            return (G) "OK";
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
        
    }
}
