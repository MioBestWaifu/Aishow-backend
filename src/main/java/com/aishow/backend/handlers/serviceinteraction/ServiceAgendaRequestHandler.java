package com.aishow.backend.handlers.serviceinteraction;

import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.managers.DatabaseConnection;

public class ServiceAgendaRequestHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        return null;
    }

    //Verificacao de ID com o AUTH
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        return (G)DatabaseConnection.getScheduleByProvider(Integer.parseInt(params[0]));
    }  
}
