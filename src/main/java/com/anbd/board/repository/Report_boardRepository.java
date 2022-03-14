package com.anbd.board.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.Report_boardEntity;

public interface Report_boardRepository extends JpaRepository<Report_boardEntity, Integer>{

	@Query("select rb from Report_boardEntity rb where rb.report_client_id = :report_client_id and rb.report_board.client_id = :report_board_client_id and rb.report.board_no = :report_board_no ")
	Report_boardEntity Report_board(@Param("report_client_id") String report_client_id,@Param("report_board_client_id") String report_board_client_id,  @Param("report_board_no") Integer report_board_no);
}
