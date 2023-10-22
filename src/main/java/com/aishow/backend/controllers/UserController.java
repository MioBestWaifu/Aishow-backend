package com.aishow.backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aishow.backend.handlers.personalinteraction.LoginHandler;
import com.aishow.backend.handlers.personalinteraction.RegisterHandler;
import com.aishow.backend.handlers.personalinteraction.ReloadUserHandler;
import com.aishow.backend.handlers.userinteraction.UserRequestHandler;
import com.aishow.backend.models.UserInformation;

@CrossOrigin(origins = {"http://yancosta.online","http://www.yancosta.online"})
@RestController("api/users")
public class UserController {
    
    @GetMapping(value="/",produces = "application/json")
	public UserInformation getUser(@RequestParam("id") String id){
		return new UserRequestHandler().handle(null, new String[]{id});
	}

    //tirar esse id
    @GetMapping(value="/reload",produces = "application/json")
	public UserInformation reload(@RequestParam("id") String id){
		return new ReloadUserHandler().handle(null, new String[]{id});
	}

    @PostMapping(value ="/login", consumes = "application/json", produces = "application/json")
	public UserInformation tryToLogin(@RequestBody UserInformation loginInfo) {
		UserInformation x = new LoginHandler().handle(loginInfo);
		return x;
	}

	//PASSED
	@PostMapping(value="/register",produces ="text/plain")
	public String tryToRegister(@RequestBody UserInformation loginInfo) {
		String x = new RegisterHandler().handle(loginInfo);
		return x;
	}
}
