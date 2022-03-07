package com.anbd.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>{
	@Query("select b from BoardEntity b ORDER BY board_no DESC")
	List<BoardEntity> BoardAll();	
	
	@Query("select b from BoardEntity b where b.board.category_board_no = :board_category_board_no ORDER BY board_no DESC")
	List<BoardEntity> Board_category(@Param("board_category_board_no") int board_category_board_no);	
}
