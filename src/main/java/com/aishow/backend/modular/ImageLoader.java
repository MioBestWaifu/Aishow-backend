package com.aishow.backend.modular;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.aishow.backend.managers.Utils;

@RestController
public class ImageLoader {
    @GetMapping(value="/images/**",produces="image/png")
	public byte[] get(HttpServletRequest request) throws IOException {
        System.out.println("REQ IMAGEM");
        System.out.println(request.getRequestURI());
        System.out.println("------------------------------");
		return Utils.imageToByteArray("src/raw/"+request.getRequestURI(), "png");
	}
}
