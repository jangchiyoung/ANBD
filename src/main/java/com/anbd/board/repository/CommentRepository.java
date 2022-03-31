package com.anbd.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer>{

	@Query("select c from CommentEntity c where c.comment.board_no = :board_no")
	List<CommentEntity> commentList(@Param("board_no") int board_no);
	
	@Query("select c from CommentEntity c where c.comment_no = :comment_no")
	CommentEntity findComment(@Param("comment_no") int comment_no);
	
	@Query("select count(*) from CommentEntity c where c.comment.board_no = :comment_board_no")
	int commentCnt(@Param("comment_board_no") int comment_board_no);
}
