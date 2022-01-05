package com.anbd.board.controller;

import java.security.NoSuchAlgorithmException;

import javax.print.attribute.HashAttributeSet;

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
	@RequestMapping(value = "/anbd/join")
	public String join() {
		return "join";
	}
	
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
