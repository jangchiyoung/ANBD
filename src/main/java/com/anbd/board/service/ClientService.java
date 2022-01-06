package com.anbd.board.service;

import com.anbd.board.entity.ClientEntity;
import com.anbd.board.model.Client;

public interface ClientService {
	default ClientEntity toEntity(Client dto) {
		return ClientEntity.builder()
				.client_id(dto.getClient_id())
				.client_password(dto.getClient_password())
				.client_name(dto.getClient_name())
				.client_nickname(dto.getClient_nickname())
				.client_tel(dto.getClient_tel())
				.client_email(dto.getClient_email())
				.client_address(dto.getClient_address())
				.client_img(dto.getClient_img())
				.client_status(dto.getClient_status())
				.build();
	}
	
	default Client toDto(ClientEntity entity) {
		return Client.builder()
				.client_id(entity.getClient_id())
				.client_password(entity.getClient_password())
				.client_name(entity.getClient_name())
				.client_nickname(entity.getClient_nickname())
				.client_tel(entity.getClient_tel())
				.client_email(entity.getClient_email())
				.client_address(entity.getClient_address())
				.client_img(entity.getClient_img())
				.client_status(entity.getClient_status())
				.build();
	}
}
