package com.aishow.backend.handlers.serviceinteraction;

import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.managers.UserConnectionManager;
import com.aishow.backend.models.ServiceInformation;

public class UpdateServiceHandler extends BaseHandler{

    //AUTH SECURITY
    @Override
    public <T, G> G handle(T reqBody) {
        if(updateTemplate((ServiceInformation)reqBody)){
            if (updateAvailability((ServiceInformation)reqBody)){
                return (G)"OK";
            } else {
                return (G)"FAIL AVAILABILITY";
            }
        } else {
            return (G) "FAIL BASIC";
        }
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean updateTemplate(ServiceInformation info){
        try{
            PreparedStatement st = StatementPreparer.updateService(DatabaseConnection.getConnection(), info);
            int x = DatabaseConnection.runUpdate(st);
            return x == 1;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateAvailability(ServiceInformation info){
        try {
            PreparedStatement st = StatementPreparer.deleteleAvailabilityByTemplateId(DatabaseConnection.getConnection(), info.getTemplateId());
            DatabaseConnection.runUpdate(st);

            //Não dá pra saber se esse carlaho deu certo
            for(int a = 0; a<=6;a++){
                try{
                    if (info.getAvailableDays()[a] == true){
                        st = StatementPreparer.addAvailability(DatabaseConnection.getConnection(), 
                        info.getTemplateId(), a, info.getFromsAsTime()[a], info.getTosAsTime()[a]);
                        DatabaseConnection.runUpdate(st);
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            return true;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
