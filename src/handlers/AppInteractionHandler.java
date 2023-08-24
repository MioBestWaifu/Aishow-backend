package handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import info.UserConnection;
import managers.DatabaseConnection;
import managers.UserConnectionManager;
import managers.Utils;

public class AppInteractionHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var params = Utils.queryToMap(exchange.getRequestURI().getQuery());
        String toSend;

        if (UserConnectionManager.hasIp(exchange.getRemoteAddress().getHostString())){
            UserConnection conn = UserConnectionManager.getConnection(exchange.getRemoteAddress().getHostString());
            
            //é um refresh
            if (params == null){
                UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).setServiceRecs(DatabaseConnection.getServiceRecommendations(UserConnectionManager.getInformation(exchange.getRemoteAddress().getHostString()).getUserId()));
                new InitHandler().sendInitialContent("/", exchange);
                return;
            }

            if (params.get("type") != null){
                switch (params.get("type")){
                    case "target":
                        if(exchange.getRequestMethod().equals("POST")){
                            conn.lastPage = new String(exchange.getRequestBody().readAllBytes(),StandardCharsets.UTF_8);
                            exchange.getResponseHeaders().add("Content-type", "text/plain");
                            Utils.sendAndClose(exchange, 200, "".getBytes());
                        } else if (exchange.getRequestMethod().equals("GET")){
                            exchange.getResponseHeaders().add("Content-type", "text/plain");
                            Utils.sendAndClose(exchange, 200, conn.lastPage.getBytes(StandardCharsets.UTF_8));
                        }
                        break;
                    case "serviceImageUrl":
                        toSend = "http://"+Utils.ipAddress+"images/services/"+DatabaseConnection.getServiceImageUrl(Integer.parseInt(params.get("id")));
                        Utils.sendAndClose(exchange, 200, toSend.getBytes(StandardCharsets.UTF_8));
                        break;
                    case "userImageUrl":
                        toSend = "http://"+Utils.ipAddress+"images/"+DatabaseConnection.getUserImageUrl(Integer.parseInt(params.get("id")));
                        Utils.sendAndClose(exchange, 200, toSend.getBytes(StandardCharsets.UTF_8));
                        break;
                    case "establish":
                        Utils.sendAndClose(exchange, 200, "penis".getBytes());
                        break;
                }
            }
        } else {
            //Criando conexão
            if(params != null && params.get("type").equals("establish")){
                UserConnectionManager.addConnection(exchange.getRemoteAddress().getHostString());
                UserConnectionManager.getConnection(exchange.getRemoteAddress().getHostString()).lastPage = "/pages/login";
                Utils.sendAndClose(exchange, 200, "penis".getBytes());
                return;
        }
        }
    }


    
}
