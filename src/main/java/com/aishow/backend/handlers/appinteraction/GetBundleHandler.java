package com.aishow.backend.handlers.appinteraction;

import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.managers.DatabaseConnection;

public class GetBundleHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody) {
        return(G)DatabaseConnection.getAnotherBundle((Integer[]) reqBody);
    }

    @Override
    public <T, G> G handle(T reqBody, String[] params) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
