package handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import managers.DatabaseConnection;
import managers.Utils;

public class InfoHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var params = Utils.queryToMap(exchange.getRequestURI().getQuery());

        if(params != null){
            switch (params.get("type")) {
                case "request":
                    switch(params.get("category")){
                        case "areas":
                            sendAreas(exchange);
                            break;
                        case "mod":
                            sendMods(exchange);
                            break;
                        case "cat":
                            sendCats(exchange);
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void sendAreas(HttpExchange exchange) throws IOException{
        byte[] toSend = DatabaseConnection.GetGenericInfo("areas","idArea","name").getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-type", "application/json");
        Utils.sendAndClose(exchange,200,toSend);
    }

    private void sendMods(HttpExchange exchange) throws IOException{
        byte[] toSend = DatabaseConnection.GetGenericInfo("servicemodality","idServiceModality","modalityName").getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-type", "application/json");
        Utils.sendAndClose(exchange,200,toSend);
    }

    private void sendCats(HttpExchange exchange) throws IOException{
        byte[] toSend = DatabaseConnection.GetGenericInfo("servicecategory","idServiceCategory","categoryName").getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-type", "application/json");
        Utils.sendAndClose(exchange,200,toSend);
    }
    
}
