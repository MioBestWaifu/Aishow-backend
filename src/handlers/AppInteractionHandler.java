package handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class AppInteractionHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var toSend = "testando".getBytes();
        exchange.sendResponseHeaders(200,toSend.length);
        exchange.getResponseBody().write(toSend);
        exchange.getResponseBody().flush();
        exchange.getResponseBody().close();
    }
}
