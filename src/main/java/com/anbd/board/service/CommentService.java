package com.anbd.board.service;

import com.anbd.board.entity.BoardEntity;
import com.anbd.board.entity.CommentEntity;
import com.anbd.board.model.Comment;

public interface CommentService {
	default CommentEntity toEntity(Comment dto) {
		BoardEntity comment_board_no = BoardEntity.builder().board_no(dto.getComment_board_no()).build();
		return CommentEntity.builder()
				.comment_no(dto.getComment_no())
				.comment(comment_board_no)
				.comment_client_id(dto.getComment_client_id())
				.comment_content(dto.getComment_content())
				.comment_date(dto.getComment_date())
				.build();
	}
	
	default Comment toDto(CommentEntity entity) {
		return Comment.builder()
				.comment_no(entity.getComment_no())
				.comment_board_no(entity.getComment().getBoard_no())
				.comment_client_id(entity.getComment_client_id())
				.comment_content(entity.getComment_content())
				.comment_date(entity.getComment_date())
				.build();
	}
}
