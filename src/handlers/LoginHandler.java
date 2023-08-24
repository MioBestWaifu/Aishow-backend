package handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import info.UserInformation;
import managers.DatabaseConnection;
import managers.UserConnectionManager;
import managers.Utils;

public class LoginHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String x = new String(exchange.getRequestBody().readAllBytes(),StandardCharsets.UTF_8);
        byte[] toSend;
        UserInformation info = new UserInformation(x);
        if (DatabaseConnection.checkLogin(info)){
            info = DatabaseConnection.getActiveUserInformation(info);
            toSend = info.toJson().getBytes(StandardCharsets.UTF_8);
            UserConnectionManager.setConnectionUserInfo(exchange.getRemoteAddress().getHostString(), info);
            exchange.getResponseHeaders().add("Content-type", "application/json");
            Utils.sendAndClose(exchange,200,toSend);
        } else {
            var errorInformation = new UserInformation();
            errorInformation.setEmail("NULL");
            toSend = errorInformation.toJson().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-type", "application/json");
            Utils.sendAndClose(exchange,401,toSend);
        }
    }
    
}
