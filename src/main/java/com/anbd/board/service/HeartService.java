package com.anbd.board.service;

import com.anbd.board.entity.BoardEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.HeartEntity;
import com.anbd.board.model.Heart;

public interface HeartService {
	
	default HeartEntity toEntity(Heart dto) {
		BoardEntity heart_board_no = BoardEntity.builder().board_no(dto.getHeart_board_no()).build();
		ClientEntity heart_client_id = ClientEntity.builder().client_id(dto.getHeart_client_id()).build();
		return HeartEntity.builder()
				.heart_no(dto.getHeart_no())
				.heart_board_no(heart_board_no)
				.heart_client_id(heart_client_id)
				.build();
	}
	
	default Heart toDto(HeartEntity entity) {
		return Heart.builder()
				.heart_no(entity.getHeart_no())
				.heart_board_no(entity.getHeart_board_no().getBoard_no())
				.heart_client_id(entity.getHeart_client_id().getClient_id())
				.build();
	}
}
