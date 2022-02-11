package com.anbd.board.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.anbd.board.model.Client;
import com.anbd.board.model.Product;

import org.springframework.web.servlet.ModelAndView;

import com.anbd.board.repository.ChatRepository;
import com.anbd.board.repository.ClientRepository;
import com.anbd.board.repository.ProductRepository;
import com.anbd.board.service.ChatService;
import com.anbd.board.service.ClientService;
import com.anbd.board.service.ProductService;
import com.anbd.board.entity.ChatEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Chat;
import com.anbd.board.model.ChatRoom;
import com.anbd.board.model.Client;



@Controller
public class ChatController {

	@Autowired
	ChatRepository repository;
	
	@Autowired
	ClientRepository c_repository;
	
	@Autowired
	ProductRepository p_repository;
	
	@Autowired
	ChatService service;
	
	@Autowired
	ClientService c_service;
	
	@Autowired
	ProductService p_service;
	
	// 채팅방 목록 조회
	@RequestMapping(value = "/anbd/messages")
	public String message(HttpSession session, Model model) {
		Client user = (Client) session.getAttribute("client");
		if (user == null) {
			return "redirect:/anbd";
		}

		List<Chat> roomlist = new ArrayList<Chat>();
		List<ChatEntity> entities = repository.findRoom(user.getClient_id());
		for (ChatEntity entity : entities) {
			roomlist.add(service.toDto(entity));
		}
		List<Product> productlist = new ArrayList<Product>();
		if (roomlist != null && roomlist.size() >= 1) {
			for (Chat temp : roomlist) {
				int product_no = temp.getChat_product_no();
				String sendId = temp.getChat_send_client_id(); //보낸이
				String receiveId = temp.getChat_receive_client_id(); //받는이
				if (user.getClient_id().equals(sendId)) {
					ClientEntity c_entities = c_repository.findId(receiveId);
					ProductEntity p_entities = p_repository.findSeller(receiveId,product_no);
					model.addAttribute("client",c_entities);
					model.addAttribute("product",p_entities);
				} else {
					ClientEntity c_entities = c_repository.findId(sendId);
					ProductEntity p_entities = p_repository.findSeller(sendId,product_no);
					model.addAttribute("client",c_entities);
					model.addAttribute("product",p_entities);
				}
			}
		}
		model.addAttribute("roomlist",roomlist);
		model.addAttribute("productlist",productlist);
		return "message";
	}
	
	
	// 채팅방 조회
		@SuppressWarnings("unchecked")
		@ResponseBody
		@RequestMapping(value = "/anbd/message", method = RequestMethod.POST)
		public JSONObject getRoom(@RequestBody ChatRoom room, HttpServletRequest request) {
			int product_no = room.getProduct_no();
			String send_client_id = room.getSend_id();
			String receive_client_id = room.getReceive_id();
			LocalDateTime date = room.getDate();
			List<ChatEntity> list = repository.findSendAndReceive(product_no,send_client_id, receive_client_id);
			List<Chat> chatlist = new ArrayList<Chat>();
			for (ChatEntity temp : list) {
				chatlist.add(service.toDto(temp));
			}
			// 방이 없다면
			if (chatlist == null || chatlist.size() <= 0) {
				Chat chat = new Chat();
				chat.setChat_no(0);
				chat.setChat_product_no(room.getProduct_no());
				chat.setChat_message("채팅방에 입장하셨습니다.");
				chat.setChat_send_client_id(room.getSend_id());
				chat.setChat_receive_client_id(room.getReceive_id());
				chat.setChat_date(room.getDate());
				chat.setChat_status(1);
				repository.save(service.toEntity(chat));
			}
			JSONArray jsonlist = new JSONArray();
			JSONObject result = new JSONObject();
			for (Chat index : chatlist) {
				JSONObject temp = new JSONObject();
				temp.put("product_no", index.getChat_product_no());
				temp.put("sender", index.getChat_send_client_id());
				temp.put("receiver", index.getChat_receive_client_id());
				temp.put("message", index.getChat_message());
				temp.put("date", index.getChat_date());
				jsonlist.add(temp);
			}
			result.put("list", jsonlist);
			return result;
		}
		@RequestMapping(value = "/anbd/message", method = RequestMethod.GET)
		public String getRoom(int product_no, HttpServletRequest request) {
			HttpSession session = request.getSession();
			Client client = null;
			String findSeller = p_repository.findSeller(product_no);
			client = (Client) session.getAttribute("client");
			List<ChatEntity> list = repository.findchatlist(product_no, client.getClient_id());
			List<Chat> chatlist = new ArrayList<Chat>();
			for (ChatEntity temp : list) {
				chatlist.add(service.toDto(temp));
			}
			LocalDateTime date = LocalDateTime.now();
			if (chatlist == null || chatlist.size() <= 0) {
				Chat chat = new Chat();
				chat.setChat_no(0);
				chat.setChat_product_no(product_no);
				chat.setChat_message("채팅방에 입장하셨습니다.");
				chat.setChat_send_client_id(client.getClient_id());
				chat.setChat_receive_client_id(findSeller);
				chat.setChat_date(date);
				chat.setChat_status(1);
				repository.save(service.toEntity(chat));
			}

			return "redirect:/anbd/messages";
		}
		
}
