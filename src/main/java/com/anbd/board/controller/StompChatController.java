package com.anbd.board.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.anbd.board.entity.ChatEntity;
import com.anbd.board.model.Chat;
import com.anbd.board.repository.ChatRepository;
import com.anbd.board.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StompChatController {

	private final SimpMessagingTemplate template; // 특정 Broker로 메세지를 전달

	@Autowired
	ChatRepository repository;

	@Autowired
	ChatService service;

	// Client가 SEND할 수 있는 경로
	// stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
	// "/pub/chat/enter"
	@MessageMapping(value = "/chat/enter")
	public void enter(Chat message) {
		message.setChat_message(message.getChat_send_client_id() + "님이 채팅방에 참여하였습니다.");
		template.convertAndSend("/sub/chat/room/" + message.getChat_product_no(), message);
		LocalDateTime date = LocalDateTime.now();
		if(message != null) {
			message.setChat_no(0);
			message.setChat_date(date);
			message.setChat_status(1);
			ChatEntity entity = service.toEntity(message);
			repository.save(entity);
		}
	}

	@MessageMapping(value = "/chat/message")
	public void message(Chat message) {
		template.convertAndSend("/sub/chat/room/" + message.getChat_product_no(), message);
		if(message != null) {
			message.setChat_no(0);
			message.setChat_status(1);
			ChatEntity entity = service.toEntity(message);
			repository.save(entity);
		}
	}
}