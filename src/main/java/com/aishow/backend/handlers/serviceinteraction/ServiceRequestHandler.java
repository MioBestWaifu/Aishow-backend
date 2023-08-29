package com.aishow.backend.handlers.serviceinteraction;

import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.managers.DatabaseConnection;

public class ServiceRequestHandler extends BaseHandler{

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
        return (G) DatabaseConnection.getFullServiceInformation(Integer.parseInt(params[0]),true);
    }            
}
