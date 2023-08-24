import java.net.InetSocketAddress;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;

import com.sun.net.httpserver.HttpServer;

import handlers.ImageRequestHandler;
import handlers.InfoHandler;
import handlers.InitHandler;
import handlers.LoginHandler;
import handlers.AppInteractionHandler;
import handlers.PersonalInteractionHandler;
import handlers.RegisterHandler;
import handlers.ServiceInteractionHandler;
import handlers.UserInteractionHandler;
import info.ServiceInformation;
import info.UserInformation;
import managers.DatabaseConnection;
import managers.Utils;
public class App {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 99);
        Utils.init();
        DatabaseConnection.connect();
        InitHandler initHandler = new InitHandler();
        server.createContext("/", initHandler);
        server.createContext("/login", new LoginHandler());
        server.createContext("/registering", new RegisterHandler());
        server.createContext("/pages", new AppInteractionHandler());
        server.createContext("/images", new ImageRequestHandler());
        server.createContext("/info",new InfoHandler());
        server.createContext("/personal",new PersonalInteractionHandler());
        server.createContext("/users", new UserInteractionHandler());
        server.createContext("/services", new ServiceInteractionHandler());
        server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(4));
        server.start(); 
    }
}
