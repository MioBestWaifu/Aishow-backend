package com.aishow.backend;

//Spring
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aishow.backend.managers.DatabaseManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

}
