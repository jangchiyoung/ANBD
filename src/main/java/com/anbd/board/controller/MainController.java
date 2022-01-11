package com.anbd.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	
	@RequestMapping(value = "/anbd/main")
	public String main() {
		return "main";
	}
}
