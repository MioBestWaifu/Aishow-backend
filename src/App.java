import java.net.InetSocketAddress;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;

import com.sun.net.httpserver.HttpServer;

import handlers.AppInteractionHandler;

@SpringBootApplication
@RestController
public class App {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("/")
	String sayHello() {
		return "Hello World!";
	}
}
