package com.aishow.backend.handlers.serviceinteraction;

import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.info.ClientServiceInteraction;
import com.aishow.backend.managers.DatabaseConnection;

public class ScheduleServiceHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        //Isso precisa de validação
        try{
            var y = (ClientServiceInteraction) reqBody;
            var x = DatabaseConnection.checkAvailability(y);
            if (!x.equals("AVA"))
                return (G)x;
            DatabaseConnection.addNewServiceRequest(y);
            return (G)"OK";
        } catch(Exception ex){
            return (G)"FAIL";
        }
    }
    

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        return null;
    }
}
