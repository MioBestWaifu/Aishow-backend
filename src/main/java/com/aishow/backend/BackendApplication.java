package com.aishow.backend;

//Spring
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aishow.backend.handlers.ImageUpdateHandler;
import com.aishow.backend.handlers.appinteraction.MacroInfoHandler;
import com.aishow.backend.handlers.appinteraction.PathfindHandler;
import com.aishow.backend.handlers.personalinteraction.LoginHandler;
import com.aishow.backend.handlers.personalinteraction.RegisterHandler;
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
/*
 * TODO #2 Testar as classes recém-migradas (PERSONAL):
 * ImageUpdate
 * NameUpdate
 * AnswerRequest
 * Reload
 */

 /*
  * TODO #3 Testar as classem recém-migradas (SERVICES):
  Create
  Schedule
  Agenda
  Request
  Update
  UserServicesRequest
  */

  //TODO #5 COMPATIBILIZAR COMPLETAMENTE OS TIPOS COM O JSON DO SPRING
	public static void main(String[] args) throws IOException {
		DatabaseConnection.connect();
		SpringApplication.run(BackendApplication.class, args);
	}
	// server.createContext("/", initHandler); TRANSFERIDO 
    // server.createContext("/login", new LoginHandler()); FEITO
    // server.createContext("/registering", new RegisterHandler()); FEITO
    // server.createContext("/pages", new AppInteractionHandler()); FEITO/TRANSFERIDO PARA O SPRING AUTH
    // server.createContext("/images", new ImageRequestHandler()); FEITO
    // server.createContext("/info",new InfoHandler()); FEITO
    // server.createContext("/personal",new PersonalInteractionHandler());
    // server.createContext("/users", new UserInteractionHandler());
    // server.createContext("/services", new ServiceInteractionHandler());

	//GETS
	@GetMapping("/api/info")
	public String getGenericInfo(@RequestParam("category") String cat) throws IOException{
		String x = new MacroInfoHandler().handle(null, new String[]{cat});
		return x;
	}

	@GetMapping("/api/findImage")
	public String getImageUrl(@RequestParam("type") String type, @RequestParam("id") String id){
		return new PathfindHandler().handle(null, new String[]{type,id});
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

	@PostMapping(value="/api/imageUpdate",consumes = "image/*", produces = "text/plain")
	public String tryToUpdateImage(@RequestBody byte[] image, @RequestParam("type") String type, @RequestParam("id") String id){
		return new ImageUpdateHandler().handle(image, new String[]{type,id});
	}

}
