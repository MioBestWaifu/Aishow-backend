package handlers;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import managers.Utils;
public class ImageRequestHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] toSend = Utils.imageToByteArray("src/raw/"+exchange.getRequestURI().toString(), "png");
        exchange.getResponseHeaders().add("Content-type", "image/png");
        Utils.sendAndClose(exchange,200,toSend);
    }
    
}
