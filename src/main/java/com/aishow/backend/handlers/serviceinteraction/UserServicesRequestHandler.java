package com.aishow.backend.handlers.serviceinteraction;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.handlers.BaseHandler;

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
        return (G) DatabaseConnection.getUserServices(Integer.parseInt(params[0]));
    }
}
