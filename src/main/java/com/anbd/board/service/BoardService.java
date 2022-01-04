package com.anbd.board.service;

import com.anbd.board.entity.BoardEntity;
import com.anbd.board.entity.Category_boardEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.model.Board;

public interface BoardService {

	default BoardEntity toEntity(Board dto) {
		ClientEntity board_client_id = ClientEntity.builder().client_id(dto.getBoard_client_id()).build();
		Category_boardEntity board_category = Category_boardEntity.builder().category_board_name(dto.getBoard_category()).build();
		return BoardEntity.builder()
				.board_no(dto.getBoard_no())
				.board_client_id(board_client_id)
				.board_category(board_category)
				.board_img1(dto.getBoard_img1())
				.board_img2(dto.getBoard_img2())
				.board_img3(dto.getBoard_img3())
				.board_content(dto.getBoard_content())
				.board_date(dto.getBoard_date())
				.board_count(dto.getBoard_count())
				.board_like(dto.getBoard_like())
				.build();
	}
	
	default Board toDto(BoardEntity entity) {
		return Board.builder()
				.board_no(entity.getBoard_no())
				.board_client_id(entity.getBoard_client_id().getClient_id())
				.board_category(entity.getBoard_category().getCategory_board_name())
				.board_img1(entity.getBoard_img1())
				.board_img2(entity.getBoard_img2())
				.board_img3(entity.getBoard_img3())
				.board_content(entity.getBoard_content())
				.board_date(entity.getBoard_date())
				.board_count(entity.getBoard_count())
				.board_like(entity.getBoard_like())
				.build();
			
	}
}
