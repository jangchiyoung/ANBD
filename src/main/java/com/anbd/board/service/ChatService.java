package com.anbd.board.service;

import com.anbd.board.entity.ChatEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.model.Chat;

public interface ChatService {
	default ChatEntity toEntity(Chat dto) {
		ClientEntity chat_seller_id = ClientEntity.builder().client_id(dto.getChat_seller_id()).build();
		ClientEntity chat_buyer_id = ClientEntity.builder().client_id(dto.getChat_buyer_id()).build();
		return ChatEntity.builder()
				.chat_no(dto.getChat_no())
				.chat_roomid(dto.getChat_roomid())
				.chat_message(dto.getChat_message())
				.chat_seller_id(chat_seller_id)
				.chat_buyer_id(chat_buyer_id)
				.chat_date(dto.getChat_date())
				.chat_date(dto.getChat_date())
				.chat_status(dto.getChat_status())
				.build();
	}
	
	default Chat toDto(ChatEntity entity) {
		return Chat.builder()
				.chat_no(entity.getChat_no())
				.chat_roomid(entity.getChat_roomid())
				.chat_message(entity.getChat_message())
				.chat_seller_id(entity.getChat_seller_id().getClient_id())
				.chat_buyer_id(entity.getChat_buyer_id().getClient_id())
				.chat_date(entity.getChat_date())
				.chat_status(entity.getChat_status())
				.build();
	}
}
