package com.aishow.backend.handlers.personalinteraction;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.handlers.BaseHandler;

public class NameUpdateHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }


    //Parar de receber o user como parametro como AUTH
    /**
     * @param reqBody texto do novo nome
     * @param params idUser no 0
     */
    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        if (DatabaseConnection.tryToUpdateUserName(Integer.parseInt(params[0]),(String) reqBody)) {
            //UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).setName(newName);
            return (G) "OK";
        } else {
            return (G) "FAIL";
        }
    }
}
