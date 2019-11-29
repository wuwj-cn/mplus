package com.mplus.auth.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/auth/api/v1")
public class AuthController {

	@Value("${mplus.auth.loginProcessingUrl}")
	String loginProcessingUrl;
	
	private final Random rnd = new Random(System.currentTimeMillis());
	
	@GetMapping("/login")
    public String login(Model model, 
    					@RequestParam(value = "error", required = false) String error) throws Exception {
		Thread.sleep(rnd.nextInt(2000));
        if (error != null) {
            model.addAttribute("error", "用户名或密码错误");
        }
        model.addAttribute("loginProcessingUrl", loginProcessingUrl);
        return "login";
    }
}
