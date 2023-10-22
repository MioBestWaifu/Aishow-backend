package com.aishow.backend.handlers.serviceinteraction;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.models.ServiceInformation;

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
        var x = (ServiceInformation) reqBody;
        x.setProviderId(Integer.parseInt(params[0]));
        if(DatabaseConnection.tryToAddServiceTemplate(x)){
            return (G) "OK";
        } else {
            return (G) "FAIL";
        }
    }
}
