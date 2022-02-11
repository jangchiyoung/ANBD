package com.anbd.board.service;

import com.anbd.board.entity.ChatEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Chat;

public interface ChatService {
	default ChatEntity toEntity(Chat dto) {
		ClientEntity chat_seller_id = ClientEntity.builder().client_id(dto.getChat_send_client_id()).build();
		ClientEntity chat_buyer_id = ClientEntity.builder().client_id(dto.getChat_receive_client_id()).build();
		ProductEntity chat_product_no = ProductEntity.builder().product_no(dto.getChat_product_no()).build();
		return ChatEntity.builder()
				.chat_no(dto.getChat_no())
				.chat(chat_product_no)
				.chat_message(dto.getChat_message())
				.chat_send(chat_seller_id)
				.chat_receive(chat_buyer_id)
				.chat_date(dto.getChat_date())
				.chat_date(dto.getChat_date())
				.chat_status(dto.getChat_status())
				.build();
	}
	
	default Chat toDto(ChatEntity entity) {
		return Chat.builder()
				.chat_no(entity.getChat_no())
				.chat_product_no(entity.getChat().getProduct_no())
				.chat_message(entity.getChat_message())
				.chat_send_client_id(entity.getChat_send().getClient_id())
				.chat_receive_client_id(entity.getChat_receive().getClient_id())
				.chat_date(entity.getChat_date())
				.chat_status(entity.getChat_status())
				.build();
	}
}
