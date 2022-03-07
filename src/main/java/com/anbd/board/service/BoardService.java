package com.anbd.board.service;

import com.anbd.board.entity.BoardEntity;
import com.anbd.board.entity.Category_boardEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.model.Board;

public interface BoardService {

	default BoardEntity toEntity(Board dto) {
		ClientEntity board_client_id = ClientEntity.builder().client_id(dto.getBoard_client_id()).build();
		Category_boardEntity board_category = Category_boardEntity.builder().category_board_no(dto.getBoard_category_no()).build();
		return BoardEntity.builder()
				.board_no(dto.getBoard_no())
				.board_writer(board_client_id)
				.board(board_category)
				.board_img1(dto.getBoard_img1())
				.board_img2(dto.getBoard_img2())
				.board_img3(dto.getBoard_img3())
				.board_name(dto.getBoard_name())
				.board_content(dto.getBoard_content())
				.board_status(dto.getBoard_status())
				.board_date(dto.getBoard_date())
				.board_readcount(dto.getBoard_readcount())
				.board_like(dto.getBoard_like())
				.board_address(dto.getBoard_address())
				.build();
	}
	
	default Board toDto(BoardEntity entity) {
		return Board.builder()
				.board_no(entity.getBoard_no())
				.board_client_id(entity.getBoard_writer().getClient_id())
				.board_category_no(entity.getBoard().getCategory_board_no())
				.board_img1(entity.getBoard_img1())
				.board_img2(entity.getBoard_img2())
				.board_img3(entity.getBoard_img3())
				.board_name(entity.getBoard_name())
				.board_content(entity.getBoard_content())
				.board_status(entity.getBoard_status())
				.board_date(entity.getBoard_date())
				.board_readcount(entity.getBoard_readcount())
				.board_like(entity.getBoard_like())
				.build();
			
	}
}
