package com.aishow.backend.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aishow.backend.handlers.appinteraction.ReviewPossilibilityHandler;
import com.aishow.backend.handlers.appinteraction.ReviewRequestHandler;
import com.aishow.backend.handlers.personalinteraction.GeoLimitationUpdateHandler;
import com.aishow.backend.handlers.personalinteraction.GeoLimitationsRequestHandler;
import com.aishow.backend.handlers.personalinteraction.LoginHandler;
import com.aishow.backend.handlers.personalinteraction.NameUpdateHandler;
import com.aishow.backend.handlers.personalinteraction.RegisterHandler;
import com.aishow.backend.handlers.personalinteraction.ReloadUserHandler;
import com.aishow.backend.handlers.userinteraction.UserRequestHandler;
import com.aishow.backend.models.GeoLimitation;
import com.aishow.backend.models.ReviewInfomation;
import com.aishow.backend.models.UserInformation;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

@CrossOrigin(origins = {"http://yancosta.online","http://www.yancosta.online","http://localhost:4200","168.232.228.88"})
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping(produces = "application/json")
	public UserInformation getUser(@RequestParam("id") String id){
		return new UserRequestHandler().handle(null, new String[]{id});
	}

    //tirar esse id
	//OK
    @GetMapping(value="reload",produces = "application/json")
	public UserInformation reload(@RequestParam("id") String id){
		return new ReloadUserHandler().handle(null, new String[]{id});
	}

	@GetMapping(value="reviews",produces = "application/json")
	public ArrayList<ReviewInfomation> getReviews(@RequestParam("id") String id){
		return new ReviewRequestHandler().handle(null, new String[]{"users",id});
	}

	@GetMapping(value="checkReview",produces = "text/plain")
	public String checkReview(@RequestParam("idUser") String idUser, @RequestParam("idProvider") String idProvider){
		return new ReviewPossilibilityHandler().handle(null, new String[]{"users",idUser,idProvider});
	}

	@GetMapping(value="geoLimitations",produces = "application/json")
	public ArrayList<GeoLimitation> getGeoLimitations(@RequestParam("id") String id){
		return new GeoLimitationsRequestHandler().handle(null, new String[]{id});
	}

	//OK
    @PostMapping(value ="login", consumes = "application/json", produces = "application/json")
	public UserInformation tryToLogin(@RequestBody UserInformation loginInfo) {
		UserInformation x = new LoginHandler().handle(loginInfo);
		return x;
	}

	//OK
	@PostMapping(value="register",produces ="text/plain")
	public String tryToRegister(@RequestBody UserInformation loginInfo) {
		String x = new RegisterHandler().handle(loginInfo);
		return x;
	}

	//OK
	@PostMapping(value="updateName",consumes = "text/plain", produces = "text/plain")
	public String updateName(@RequestBody String newName,@RequestParam("id") String id) {
		String x = new NameUpdateHandler().handle(newName,new String[]{id});
		return x;
	}

	@PostMapping(value = "updateGeoLimitations", consumes = "application/json", produces = "text/plain")
	public String updateGeoLitimitaions(@RequestBody GeoLimitation[] geoLimitations) {
		return new GeoLimitationUpdateHandler().handle(geoLimitations);
	}
}
