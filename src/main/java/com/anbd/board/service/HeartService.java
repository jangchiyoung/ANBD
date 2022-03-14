package com.anbd.board.service;

import com.anbd.board.entity.HeartEntity;
import com.anbd.board.model.Heart;

public interface HeartService {
	
	default HeartEntity toEntity(Heart dto) {
		return HeartEntity.builder()
				.heart_no(dto.getHeart_no())
				.heart_board_no(dto.getHeart_board_no())
				.heart_client_id(dto.getHeart_client_id())
				.build();
	}
	
	default Heart toDto(HeartEntity entity) {
		return Heart.builder()
				.heart_no(entity.getHeart_no())
				.heart_board_no(entity.getHeart_board_no())
				.heart_client_id(entity.getHeart_client_id())
				.build();
	}
}
