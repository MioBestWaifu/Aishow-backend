import java.net.InetSocketAddress;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;

import com.sun.net.httpserver.HttpServer;

import handlers.AppInteractionHandler;
public class App {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(443), 99);
        server.createContext("/", new AppInteractionHandler());
        server.start(); 
    }
}
