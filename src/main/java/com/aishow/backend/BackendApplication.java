package com.aishow.backend;

//Spring
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//Azure
import com.azure.core.credential.*;
import com.azure.core.util.BinaryData;
import com.azure.identity.*;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.azure.storage.blob.specialized.*;
import com.azure.storage.common.*;

import com.aishow.backend.handlers.*;
import com.aishow.backend.handlers.appinteraction.*;
import com.aishow.backend.handlers.personalinteraction.*;
import com.aishow.backend.handlers.serviceinteraction.*;
import com.aishow.backend.handlers.userinteraction.*;
import com.aishow.backend.info.*;
import com.aishow.backend.managers.DatabaseConnection;
import com.aishow.backend.managers.DatabaseManager;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@CrossOrigin(origins = {"http://yancosta.online","http://www.yancosta.online"})
@RestController
public class BackendApplication {
	static String index, css, run, poly, main;
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
		var stream = new ClassPathResource("dist/hayasaka/index.html").getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		index = reader.lines().collect(Collectors.joining("\n"));
		stream.close();
		reader.close();

		stream = new ClassPathResource("dist/hayasaka/main.js").getInputStream();
		reader = new BufferedReader(new InputStreamReader(stream));
		main = reader.lines().collect(Collectors.joining("\n"));
		stream.close();
		reader.close();

		stream = new ClassPathResource("dist/hayasaka/polyfills.js").getInputStream();
		reader = new BufferedReader(new InputStreamReader(stream));
		poly = reader.lines().collect(Collectors.joining("\n"));
		stream.close();
		reader.close();

		stream = new ClassPathResource("dist/hayasaka/styles.css").getInputStream();
		reader = new BufferedReader(new InputStreamReader(stream));
		css = reader.lines().collect(Collectors.joining("\n"));
		stream.close();
		reader.close();

		stream = new ClassPathResource("dist/hayasaka/runtime.js").getInputStream();
		reader = new BufferedReader(new InputStreamReader(stream));
		run = reader.lines().collect(Collectors.joining("\n"));
		stream.close();
		reader.close();
		DatabaseManager dm = new DatabaseManager();
		dm.start();
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("PSVM");
	}

	//GETS
	@GetMapping("/")
	public String getIndex() throws IOException{
		System.out.println("Index");
		return index;
	}

	@GetMapping(value = "/main.js", produces = "application/javascript")
	public String getMain() throws IOException{
		System.out.println("Main");
		return main;
	}

	@GetMapping(value = "/polyfills.js", produces = "application/javascript")
	public String getPoly() throws IOException{
		System.out.println("Poly");
		return poly;
	}

	@GetMapping(value = "/runtime.js" , produces = "application/javascript")
	public String getRun() throws IOException{
		System.out.println("Runtime");
		return run;
	}

	@GetMapping(value = "/styles.css", produces = "text/css")
	public String getCss() throws IOException{
		System.out.println("Styles");
		return css;
	}
	
	//GETS
	//PASSED
	@GetMapping("/api/info")
	public ArrayList<GenericInformation> getGenericInfo(@RequestParam("category") String cat) throws IOException{
	    ArrayList<GenericInformation> x = new MacroInfoHandler().handle(null, new String[]{cat});
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
	public String answerRequest(@RequestParam("type")String type, @RequestParam("id") String id, @RequestParam("idProvider") String idProvider){
		return new AnswerRequestHandler().handle(null, new String[]{type,id,idProvider});
	}

	//Parar de recebr o idProvider dps do AUTH
	//PASSED
	@GetMapping(value="/api/getAgenda",produces = "application/json")
	public ServiceSchedule getAgenda(@RequestParam("id") String id){
		return new ServiceAgendaRequestHandler().handle(null, new String[]{id});
	}

	@GetMapping(value="/api/getUserRequests",produces = "application/json")
	public ArrayList<ClientServiceInteraction> getUserServiceRequests(@RequestParam("id") String id){
		return new UserMadeServiceRequestsHandler().handle(null, new String[]{id});
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

	@GetMapping(value="/api/reload",produces = "application/json")
	public UserInformation reload(@RequestParam("id") String id){
		return new ReloadUserHandler().handle(null, new String[]{id});
	}

	//PASSED
	@GetMapping(value="/api/getAllServices",produces = "application/json")
	public List<ServiceInformation> getAllUserServices(@RequestParam("id") String id){
		return new UserServicesRequestHandler().handle(null, new String[]{id});
	}

	//Transformar isso num delete
	@GetMapping(value = "/api/cancelRequest",produces = "text/plain")
	public String cancelRequest(@RequestParam("id") String id){
		return new CancelRequestHandler().handle(null, new String[]{id});
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

	@PostMapping(value = "/api/getAnotherBundle", consumes = "application/json", produces = "application/json")
	public ServiceBundle getAnortherBundle (@RequestBody Integer[] has){
		return new GetBundleHandler().handle(has);
	}

	//PASSED
	@PostMapping(value="/api/imageUpdate",consumes = "image/*", produces = "text/plain")
	public String tryToUpdateImage(@RequestBody byte[] image, @RequestParam("type") String type, @RequestParam("id") String id, @RequestParam(name = "idProvider",required = false) String idProvider){
		return new ImageUpdateHandler().handle(image, new String[]{type,id,idProvider});
	}

	//Id será desnecessario com o AUTH
	//PASSED
	@PostMapping(value="/api/updateName",consumes = "text/plain", produces = "text/plain")
	public String updateName(@RequestBody String newName,@RequestParam("id") String id) {
		String x = new NameUpdateHandler().handle(newName,new String[]{id});
		return x;
	}

	//Id será desnecessario com o AUTH
	//PASSED
	@PostMapping(value="/api/createService",consumes = "application/json", produces = "text/plain")
	public String createService(@RequestBody ServiceInformation info,@RequestParam("id") String id) {
		String x = new CreateServiceHandler().handle(info,new String[]{id});
		return x;
	}

	//PASSED
	@PostMapping(value="/api/scheduleService",consumes = "application/json", produces = "text/plain")
	public String scheduleService(@RequestBody ClientServiceInteraction info){
		return new ScheduleServiceHandler().handle(info);
	}

	//PASSED
	@PostMapping(value="/api/updateService",consumes = "application/json", produces = "text/plain")
	public String updateService(@RequestBody ServiceInformation info){
		return new UpdateServiceHandler().handle(info);
	}

}
