package com.anbd.board.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anbd.board.Hash;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.model.Client;
import com.anbd.board.repository.ClientRepository;
import com.anbd.board.service.ClientService;

@Controller
public class LoginController {

	@Autowired
	ClientRepository repository;
	
	@Autowired
	ClientService service;
	
	// 회원가입 등록
	@RequestMapping(value = "/anbd/register", method =  RequestMethod.POST)
	public String join(Client client, Model model) throws NoSuchAlgorithmException {
		String password = null;
		Hash hash = new  Hash();
		password = hash.hashtest(client.getClient_password());
		if(client != null) {
			client.setClient_img("default.png");
			client.setClient_status(0);
			client.setClient_password(password);
			ClientEntity entity = service.toEntity(client);
			repository.save(entity);
		}
		return "redirect:login";
	}
	// 회원가입 페이지
	@RequestMapping(value = "/anbd/join")
	public String join() {
		return "join";
	}
	
	
	// 로그인 페이지
	@RequestMapping(value = "/anbd/login")
	public String login(String alert, Model model) {
		if (alert != null && alert.equals("y")) {
			model.addAttribute("message", "로그인이 필요합니다.");
			model.addAttribute("url", "login");
			return "alertLogin";
		} else {

			return "login"; // 로그인 버튼 => login.jsp(뷰) -> 로그인정보입력후 버튼(사용자) ->
		}
	}
	
	// 로그아웃
	@RequestMapping(value = "/anbd/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("client") != null) {
			session.invalidate();
			return "login";
		}
		return "login";
	}
	// 로그인 체크
	@RequestMapping(value = "/anbd/logincheck", method = RequestMethod.POST)
	public String logincheck(String client_id, String client_password, Model model, HttpServletRequest request)
			throws NoSuchAlgorithmException {
		String password = null;
		Hash hash = new Hash();
		password = hash.hashtest(client_password);
		Optional<ClientEntity> client = repository.findById(client_id);
		if (client.isPresent()) {
			Client user = null;
			user = service.toDto(client.get());
			HttpSession session = request.getSession();
		if (password.equals(user.getClient_password())) {
			session.setAttribute("client", service.toDto(client.get()));
			return "redirect:/anbd/main";
		}
			String message = "로그인 정보가 다릅니다.";
			model.addAttribute("message", message);
			model.addAttribute("url", "login");
			return "alertLogin";
		} else {
			String message = "로그인 정보가 다릅니다.";
			model.addAttribute("message", message);
			model.addAttribute("url", "login");
			return "alertLogin";
		}
	}	
		
	// 아이디 중복체크
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/checkId", method = RequestMethod.POST)
	public JSONObject check_Id(@RequestBody JSONObject data) {
		JSONObject result = new JSONObject();
		if (repository.checkId(String.valueOf(data.get("id"))).isEmpty()) {
			result.put("data", 0);
			return result;
		} else {
			result.put("data", 27);
			return result;
		}

	}
	
	// 닉네임 중복체크
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/checkNickname", method = RequestMethod.POST)
	public JSONObject check_Nickname(@RequestBody JSONObject data) {
		JSONObject result = new JSONObject();
		if (repository.checkNickname(String.valueOf(data.get("userNickName"))).isEmpty()) {
			result.put("data", 0);
			return result;
		} else {
			result.put("data", 27);
			return result;
		}

	}
	
	// 이메일 중복체크
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/anbd/checkEmail", method = RequestMethod.POST)
	public JSONObject check_Email(@RequestBody JSONObject data) {
		JSONObject result = new JSONObject();
		if (repository.checkEmail(String.valueOf(data.get("email"))).isEmpty()) {
			result.put("data", 0);
			return result;
		} else {
			result.put("data", 27);
			return result;
		}

	}
}
