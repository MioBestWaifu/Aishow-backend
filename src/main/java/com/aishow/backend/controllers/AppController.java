package com.aishow.backend.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aishow.backend.handlers.appinteraction.MacroInfoHandler;
import com.aishow.backend.handlers.appinteraction.SearchHandler;
import com.aishow.backend.models.GenericInformation;

@CrossOrigin(origins = {"http://yancosta.online","http://www.yancosta.online","http://localhost:4200","168.232.228.88"})
@RestController()
@RequestMapping("/api/app")
public class AppController {

    @GetMapping("info")
	public ArrayList<GenericInformation> getGenericInfo(@RequestParam("category") String cat) throws IOException{
	    ArrayList<GenericInformation> x = new MacroInfoHandler().handle(null, new String[]{cat});
		return x;
	}

    @GetMapping(value = "search",produces = "application/json")
	public<T> ArrayList<T> search(@RequestParam("type") String type,@RequestParam("q") String q,@RequestParam("offset") String offset){
		System.out.println(q);
		return new SearchHandler().handle(null, new String[]{type,q,offset});
	} 
}
