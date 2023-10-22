package com.aishow.backend.handlers.appinteraction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.aishow.backend.handlers.BaseHandler;
import com.aishow.backend.managers.DatabaseConnection;
import com.aishow.backend.models.GenericInformation;
import com.aishow.backend.utils.Utils;

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

    private ArrayList<GenericInformation> sendAreas() throws IOException{
        return DatabaseConnection.GetAllGenericInfo("area","idArea","name");
    }

    private ArrayList<GenericInformation> sendMods() throws IOException{
        return DatabaseConnection.GetAllGenericInfo("servicemodality","idServiceModality","modalityName");
    }

    private ArrayList<GenericInformation> sendCats() throws IOException{
        return DatabaseConnection.GetAllGenericInfo("servicecategory","idServiceCategory","categoryName");
    }
    
}
