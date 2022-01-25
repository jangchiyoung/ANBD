package com.anbd.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FooterController {
	// 이용약관 페이지
	@RequestMapping(value = "/anbd/useAgreement")
	public String useAgreement() {
		return "useAgreement";
	}
	// 이용약관 페이지
	@RequestMapping(value = "/anbd/privacy")
	public String privacy() {
		return "privacy";
	}	
	// 이용약관 페이지
	@RequestMapping(value = "/anbd/location")
	public String location() {
		return "location";
	}	
}
