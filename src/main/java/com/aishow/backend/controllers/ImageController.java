package com.aishow.backend.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aishow.backend.handlers.ImageUpdateHandler;
import com.aishow.backend.handlers.appinteraction.MacroInfoHandler;
import com.aishow.backend.handlers.appinteraction.PathfindHandler;
import com.aishow.backend.models.GenericInformation;

@CrossOrigin(origins = {"http://yancosta.online","http://www.yancosta.online"})
@RestController("api/images")
public class ImageController {
    @PostMapping(value="/update",consumes = "image/*", produces = "text/plain")
	public String tryToUpdateImage(@RequestBody byte[] image, @RequestParam("type") String type, @RequestParam("id") String id, @RequestParam(name = "idProvider",required = false) String idProvider){
		return new ImageUpdateHandler().handle(image, new String[]{type,id,idProvider});
	}

    @GetMapping("/find")
	public String getImageUrl(@RequestParam("type") String type, @RequestParam("id") String id){
		return new PathfindHandler().handle(null, new String[]{type,id});
	}
}
