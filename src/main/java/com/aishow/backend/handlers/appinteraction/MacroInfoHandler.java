package com.aishow.backend.handlers.appinteraction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.managers.DatabaseConnection;
import com.aishow.backend.managers.Utils;

public class MacroInfoHandler extends BaseHandler{

    @Override
    public <T, G> G handle(T reqBody){
        return null;
    }
    
    @Override
    public <T, G> G handle(T reqBody, String[] params){
        try{
            switch(params[0]){
                case "areas":
                    return (G) sendAreas();
                case "mod":
                    return (G) sendMods();
                case "cat":
                    return (G) sendCats();
                default:
                    return (G) "ERROR";
            } 
        } catch (IOException ex){
            return null;
        }
    }

    private String sendAreas() throws IOException{
        return DatabaseConnection.GetGenericInfo("area","idArea","name");
    }

    private String sendMods() throws IOException{
        return DatabaseConnection.GetGenericInfo("servicemodality","idServiceModality","modalityName");
    }

    private String sendCats() throws IOException{
        return DatabaseConnection.GetGenericInfo("servicecategory","idServiceCategory","categoryName");
    }
    
}
