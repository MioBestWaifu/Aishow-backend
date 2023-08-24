package handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import info.UserInformation;
import managers.DatabaseConnection;
import managers.Utils;

public class RegisterHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String x = new String(exchange.getRequestBody().readAllBytes());
        if (DatabaseConnection.tryToRegister(new UserInformation(x))){
            //Checar esse krl
            exchange.getResponseHeaders().add("Content-type", "text/plain");
            Utils.sendAndClose(exchange,201, "".getBytes());
            //Esse tbm
        } else{
            exchange.getResponseHeaders().add("Content-type", "text/plain");
            Utils.sendAndClose(exchange,400,"".getBytes());
        }

    }
    
}
