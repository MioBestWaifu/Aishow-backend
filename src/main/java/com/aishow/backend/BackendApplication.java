package com.aishow.backend;

//Spring
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aishow.backend.handlers.*;
import com.aishow.backend.handlers.appinteraction.*;
import com.aishow.backend.handlers.personalinteraction.*;
import com.aishow.backend.handlers.serviceinteraction.*;
import com.aishow.backend.handlers.userinteraction.*;
import com.aishow.backend.info.*;
import com.aishow.backend.managers.DatabaseConnection;

import java.io.IOException;
//SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
  * TODO #3 Testar as classes recém-migradas (SERVICES):
  Create
  Schedule
  Agenda
  Request
  Update
  UserServicesRequest
  */

  //TODO #5 COMPATIBILIZAR COMPLETAMENTE OS TIPOS COM O JSON DO SPRING
  //TODO #6 botar try-catch em todo mundo aq e criar um log de algum tipo
	public static void main(String[] args) throws IOException {
		DatabaseConnection.connect();
		SpringApplication.run(BackendApplication.class, args);
	}

	//GETS
	//PASSED
	@GetMapping("/api/info")
	public String getGenericInfo(@RequestParam("category") String cat) throws IOException{
		String x = new MacroInfoHandler().handle(null, new String[]{cat});
		return x;
	}

	//PASSED
	@GetMapping("/api/findImage")
	public String getImageUrl(@RequestParam("type") String type, @RequestParam("id") String id){
		return new PathfindHandler().handle(null, new String[]{type,id});
	}

	//Parar de recebr o idProvider dps do AUTH
	//PASSED
	@GetMapping(value="/api/answerRequest",produces = "text/plain")
	public String answerRequest(@RequestParam("type")String type, @RequestParam("id") String id, @RequestParam("providerId") String idProvider){
		return new AnswerRequestHandler().handle(null, new String[]{type,id,idProvider});
	}

	//Parar de recebr o idProvider dps do AUTH
	//PASSED
	@GetMapping(value="/api/getAgenda",produces = "application/json")
	public ServiceSchedule getAgenda(@RequestParam("id") String id){
		return new ServiceAgendaRequestHandler().handle(null, new String[]{id});
	}

	//PASSED, ADICIONAR OBJETO PROVIDER NO FULL INFO
	@GetMapping(value="/api/getService",produces = "application/json")
	public ServiceInformation getService(@RequestParam("id") String id){
		return new ServiceRequestHandler().handle(null, new String[]{id});
	}

	//PASSED
	@GetMapping(value="/api/getUser",produces = "application/json")
	public UserInformation getUser(@RequestParam("id") String id){
		return new UserRequestHandler().handle(null, new String[]{id});
	}

	//PASSED
	@GetMapping(value="/api/getAllServices",produces = "application/json")
	public List<ServiceInformation> getAllUserServices(@RequestParam("id") String id){
		return new UserServicesRequestHandler().handle(null, new String[]{id});
	}

	//POSTS
	//PASSED
	@PostMapping(value ="/api/login", consumes = "application/json", produces = "application/json")
	public UserInformation tryToLogin(@RequestBody UserInformation loginInfo) {
		UserInformation x = new LoginHandler().handle(loginInfo);
		return x;
	}

	//PASSED
	@PostMapping(value="/api/register",produces ="text/plain")
	public String tryToRegister(@RequestBody UserInformation loginInfo) {
		String x = new RegisterHandler().handle(loginInfo);
		return x;
	}

	//PASSED
	@PostMapping(value="/api/imageUpdate",consumes = "image/*", produces = "text/plain")
	public String tryToUpdateImage(@RequestBody byte[] image, @RequestParam("type") String type, @RequestParam("id") String id){
		return new ImageUpdateHandler().handle(image, new String[]{type,id});
	}

	//Id será desnecessario com o AUTH
	//PASSED
	@PostMapping(value="/api/updateName",consumes = "text/plain", produces = "text/plain")
	public String updateName(@RequestBody String newName,@RequestParam("id") String id) {
		String x = new NameUpdateHandler().handle(newName,new String[]{id});
		return x;
	}

	//Id será desnecessario com o AUTH
	//PROBLEMA COM CONVERTER O DATE
	@PostMapping(value="/api/createService",consumes = "application/json", produces = "text/plain")
	public String createService(@RequestBody ServiceInformation info,@RequestParam("id") String id) {
		String x = new CreateServiceHandler().handle(info,new String[]{id});
		return x;
	}

	//VAI DAR O MESMO PROBLEMA DE CIMA
	@PostMapping(value="/api/scheduleService",consumes = "application/json", produces = "text/plain")
	public String scheduleService(@RequestBody ClientServiceInteraction info){
		return new ScheduleServiceHandler().handle(info);
	}

	///VAI DAR O MESMO PROBLEMA DO CREATE
	@PostMapping(value="/api/updateService",consumes = "application/json", produces = "text/plain")
	public String updateService(@RequestBody ServiceInformation info){
		return new UpdateServiceHandler().handle(info);
	}

}
