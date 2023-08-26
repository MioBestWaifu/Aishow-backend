package com.aishow.backend;

//Spring
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aishow.backend.handlers.ImageRequestHandler;
import com.aishow.backend.handlers.InfoHandler;
import com.aishow.backend.handlers.LoginHandler;
import com.aishow.backend.handlers.RegisterHandler;
import com.aishow.backend.info.UserInformation;
import com.aishow.backend.managers.DatabaseConnection;

import java.io.IOException;
//SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootApplication
@RestController
public class BackendApplication {

	public static void main(String[] args) throws IOException {
		DatabaseConnection.connect();
		SpringApplication.run(BackendApplication.class, args);
	}
	// server.createContext("/", initHandler);
    // server.createContext("/login", new LoginHandler()); FEITO
    // server.createContext("/registering", new RegisterHandler()); FEITO
    // server.createContext("/pages", new AppInteractionHandler());
    // server.createContext("/images", new ImageRequestHandler()); FEITO
    // server.createContext("/info",new InfoHandler()); FEITO
    // server.createContext("/personal",new PersonalInteractionHandler());
    // server.createContext("/users", new UserInteractionHandler());
    // server.createContext("/services", new ServiceInteractionHandler());

	//GETS
	@GetMapping("/api/info")
	public String getGenericInfo(@RequestParam("category") String cat) throws IOException{
		String x = new InfoHandler().handle(null, cat);
		return x;
	}

	//POSTS
	@PostMapping("/api/login")
	public UserInformation tryToLogin(@RequestBody UserInformation loginInfo) {
		UserInformation x = new LoginHandler().handle(loginInfo);
		return x;
	}

	@PostMapping(value="/api/register",produces ="text/plain")
	public String tryToRegister(@RequestBody UserInformation loginInfo) {
		String x = new RegisterHandler().handle(loginInfo);
		return x;
	}

}
