package com.anbd.board.service;

import com.anbd.board.entity.Category_boardEntity;
import com.anbd.board.model.Category;
import com.anbd.board.model.Category_board;

public interface Category_boardService {
	default Category_boardEntity toEntity(Category dto) {
		return Category_boardEntity.builder()
				.category_board_no(dto.getCategory_no())
				.category_board_name(dto.getCategory_name())
				.build();
	}
	
	default Category_board toDto(Category_boardEntity entity) {
		return Category_board.builder()
				.category_board_no(entity.getCategory_board_no())
				.category_board_name(entity.getCategory_board_name())
				.build();
	}
}
