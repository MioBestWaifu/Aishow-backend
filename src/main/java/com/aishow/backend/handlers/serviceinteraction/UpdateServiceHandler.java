package com.aishow.backend.handlers.serviceinteraction;

import java.nio.charset.StandardCharsets;

import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.managers.DatabaseConnection;
import com.aishow.backend.managers.UserConnectionManager;
import com.aishow.backend.models.ServiceInformation;

public class UpdateServiceHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        if(DatabaseConnection.tryToUpdateServiceTemplate((ServiceInformation)reqBody)){
            if (DatabaseConnection.updateAvailabilityScheme((ServiceInformation)reqBody)){
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
}
