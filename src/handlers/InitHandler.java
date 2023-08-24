package handlers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import managers.Utils;

public class InitHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().toString();
        byte[] toSend;
        sendInitialContent(path, exchange);
    }

    public void sendInitialContent(String path, HttpExchange exchange){
        try{
            System.out.println(path);
            switch (path){
                case "/main.js":
                    exchange.getResponseHeaders().add("Content-type", "text/javascript");
                    exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "C:/Users/yan/Documents/Programas/Web");
                    Utils.sendAndClose(exchange,200,Utils.pages.get("Main"));
                    break;
                case "/runtime.js":
                    exchange.getResponseHeaders().add("Content-type", "text/javascript");
                    Utils.sendAndClose(exchange,200,Utils.pages.get("Runtime"));
                    break;
                case "/polyfills.js":
                    exchange.getResponseHeaders().add("Content-type", "text/javascript");
                    Utils.sendAndClose(exchange,200,Utils.pages.get("Polyfills"));
                    break;
                case "/styles.css":
                    exchange.getResponseHeaders().add("Content-type", "text/css");
                    Utils.sendAndClose(exchange,200,Utils.pages.get("Styles"));
                    break;
                case "/favicon.ico":
                    exchange.getResponseHeaders().add("Content-type", "image/ico");
                    Utils.sendAndClose(exchange,200,Utils.pages.get("Favicon"));
                    break;
                case "/":
                    exchange.getResponseHeaders().add("Content-type", "text/html");
                    Utils.sendAndClose(exchange,200,Utils.pages.get("Index"));
                    break;
            }
        } catch (IOException ex){

        }
    }


    
}