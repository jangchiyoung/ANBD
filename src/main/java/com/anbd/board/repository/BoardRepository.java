package com.anbd.board.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>{
	@Query(nativeQuery = true, value = "select * from Board b where b.board_address like %:board_address% ORDER BY board_no DESC LIMIT :StartNo,:EndNo")
	List<BoardEntity> BoardAll(@Param("board_address") String board_address,@Param("StartNo") int StartNo, @Param("EndNo") int EndNo);	
	
	@Query(nativeQuery = true, value = "select * from Board b where b.board_category_board_no = :board_category_board_no and  b.board_address like %:board_address% ORDER BY board_no DESC LIMIT :StartNo,:EndNo")
	List<BoardEntity> Board_category(@Param("board_category_board_no") int board_category_board_no, @Param("board_address") String board_address,@Param("StartNo") int StartNo, @Param("EndNo") int EndNo);	
	
	@Query("select b from BoardEntity b where b.board_no = :board_no")
	BoardEntity boardDetail(@Param("board_no") int board_no);
	
	@Query("select b from BoardEntity b where b.board_writer.client_id = :board_writer")
	List<BoardEntity> findWriter(@Param("board_writer") String board_writer);
	
	@Query("select count(*) from BoardEntity b where b.board_writer.client_id = :board_writer_client_id and b.board_status = :board_status")
	Integer BoardCnt(@Param("board_writer_client_id") String board_writer_client_id, @Param("board_status") String board_status);
	
	//페이징
	@Query("select count(*) from BoardEntity b where b.board_address like CONCAT('%',:board_address,'%') ORDER BY board_no DESC")
	Integer BoardAllCnt(@Param("board_address") String board_address);
	
	@Query("select count(*) from BoardEntity b where b.board.category_board_no = :board_category_board_no and  b.board_address like CONCAT('%',:board_address,'%') ORDER BY board_no DESC")
	Integer Board_categoryCnt(@Param("board_category_board_no") int board_category_board_no, @Param("board_address") String board_address);	
	
	@Transactional
	@Modifying
	@Query(value = "update BoardEntity b set b.board_like = (select count(*) from HeartEntity h where h.heart_board_no = :board_no) where b.board_no = :board_no")
	void updateLike(@Param("board_no") Integer board_no);
	
	@Transactional
	@Modifying
	@Query(value = "update BoardEntity b set b.board_readcount = b.board_readcount + 1 where b.board_no = :board_no")
	void updateReadCount(@Param("board_no") Integer board_no);
}
