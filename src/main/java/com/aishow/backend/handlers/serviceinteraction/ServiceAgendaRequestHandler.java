package com.aishow.backend.handlers.serviceinteraction;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.handlers.BaseHandler;

public class ServiceAgendaRequestHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        return null;
    }

    //Verificacao de ID com o AUTH
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        return (G)DatabaseConnection.getScheduleByUser(Integer.parseInt(params[0]));
    }  
}
