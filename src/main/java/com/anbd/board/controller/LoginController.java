package com.anbd.board.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;

import com.anbd.board.Hash;
import com.anbd.board.RandomPassword;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.model.Client;
import com.anbd.board.repository.ClientRepository;
import com.anbd.board.service.ClientService;

@Controller
public class LoginController {
	
	@Autowired
	public JavaMailSender javaMailSender;
	
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
			String message = "비밀번호가 일치하지 않습니다";
			model.addAttribute("message", message);
			model.addAttribute("url", "login");
			return "alertLogin";
		} else {
			String message = "아이디가 존재하지 않습니다";
			model.addAttribute("message", message);
			model.addAttribute("url", "login");
			return "alertLogin";
		}
	}	
	
	// 아이디 찾기 페이지
		@RequestMapping(value = "/anbd/find_id")
		public String find_id() {
			return "find_id";
		}
	// 아이디 찾기 페이지
		@RequestMapping(value = "/anbd/find_id_C")
		public String find_id_C() {
			return "find_id";
		}
	// 아이디 찾기 체크
	@RequestMapping(value = "/anbd/findId", method = RequestMethod.POST)
	public String find_password(String client_name, String client_email, String client_tel, Model model) {
		Optional<ClientEntity> client = repository.id_find(client_name, client_email, client_tel);
		if (client.isPresent()) {
			Client user = null;
			user = service.toDto(client.get());
			model.addAttribute("client", user);
			return "find_id_C";
		}
		return "redirect:/anbd/find_id";
	}	
	// 비밀번호 찾기
	@RequestMapping(value = "/anbd/find_password")
	public String find_password() {
		return "find_password";
	}
	
	// 비밀번호 찾기 이메일 체크
	@RequestMapping(value = "/anbd/find_password_C", method = RequestMethod.POST)
	public String pw_find(String client_id, String client_email, Model model,Map<String, Object> variables) throws Exception  {
        Optional<ClientEntity> pwFindCheck = repository.userEmailCheck(client_id,client_email);
        Context context = new Context();
        context.setVariables(variables);
        String message;
        if(pwFindCheck.isPresent()) {
            
            String From= "clduddkwk@naver.com";
            String To= client_email;
            
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

            // 임시비밀번호 DB 저장
        	RandomPassword password = new RandomPassword();
        	String newPassword = password.password();
        	Hash hash = new Hash();
        	String changePassword = hash.hashtest(newPassword);
        	Client client = service.toDto(repository.getById(client_id));
			client.setClient_password(changePassword);
			repository.save(service.toEntity(client));
			
			messageHelper.setFrom(From);
            messageHelper.setTo(To);
            messageHelper.setSubject("[아나바다 마켓] 임시비밀번호 발급입니다.");
            messageHelper.setText(client_id+"님 안녕하세요.<br>"+"[아나바다 마켓] 임시비밀번호 발급입니다." + "<br>" + 
            					"로그인 하셔서 마이페이지에서 비밀번호 변경해주세요!<br>"+
            					"임시비밀번호: " +newPassword, true);
            javaMailSender.send(mimeMessage);
            
        	message = "회원님의 이메일로 임시 비밀번호를 전송하였습니다.";
        	model.addAttribute("message", message);
			model.addAttribute("url", "login");
        	return "alertLogin";
        } else {
        	message = "회원정보가 일치하지 않습니다";
			model.addAttribute("message", message);
			model.addAttribute("url", "find_password");
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
