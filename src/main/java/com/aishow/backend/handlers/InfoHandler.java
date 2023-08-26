package com.aishow.backend.handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.aishow.backend.managers.DatabaseConnection;
import com.aishow.backend.managers.Utils;

public class InfoHandler implements BaseHandler{

    @Override
    public <T, G> G handle(T reqBody){
        return null;
        // var params = Utils.queryToMap(exchange.getRequestURI().getQuery());

        // if(params != null){
        //     switch (params.get("type")) {
        //         case "request":
        //             switch(params.get("category")){
        //                 case "areas":
        //                     sendAreas(exchange);
        //                     break;
        //                 case "mod":
        //                     sendMods(exchange);
        //                     break;
        //                 case "cat":
        //                     sendCats(exchange);
        //                     break;
        //             }
        //             break;
        //         default:
        //             break;
        //     }
        // }
    }
    
    public <T, G> G handle(T reqBody, String cat) throws IOException{
        switch(cat){
            case "areas":
                return (G) sendAreas();
            case "mod":
                return (G) sendMods();
            case "cat":
                return (G) sendCats();
            default:
                return (G) "ERROR";
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
