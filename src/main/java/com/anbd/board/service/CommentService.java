package com.anbd.board.service;

import com.anbd.board.entity.BoardEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.CommentEntity;
import com.anbd.board.model.Comment;

public interface CommentService {
	default CommentEntity toEntity(Comment dto) {
		BoardEntity comment_board_no = BoardEntity.builder().board_no(dto.getComment_board_no()).build();
		ClientEntity comment_id = ClientEntity.builder().client_id(dto.getComment_id()).build();
		return CommentEntity.builder()
				.comment_no(dto.getComment_no())
				.comment_board_no(comment_board_no)
				.comment_id(comment_id)
				.comment_content(dto.getComment_content())
				.build();
	}
	
	default Comment toDto(CommentEntity entity) {
		return Comment.builder()
				.comment_no(entity.getComment_no())
				.comment_board_no(entity.getComment_board_no().getBoard_no())
				.comment_id(entity.getComment_id().getClient_id())
				.comment_content(entity.getComment_content())
				.build();
	}
}
