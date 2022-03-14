package com.anbd.board.service;

import com.anbd.board.entity.BoardEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.Report_boardEntity;
import com.anbd.board.model.Report_board;

public interface Report_boardService {
	default Report_boardEntity toEntity(Report_board dto) {
		ClientEntity report_board_client_id = ClientEntity.builder().client_id(dto.getReport_board_client_id()).build();
		BoardEntity report_board_no = BoardEntity.builder().board_no(dto.getReport_board_no()).build();
		return Report_boardEntity.builder()
				.report_no(dto.getReport_no())
				.report_client_id(dto.getReport_client_id())
				.report_board(report_board_client_id)
				.report(report_board_no)
				.report_comment(dto.getReport_comment())
				.build();
	}
	
	default Report_board toDto(Report_boardEntity entity) {
		return Report_board.builder()
				.report_no(entity.getReport_no())
				.report_client_id(entity.getReport_client_id())
				.report_board_client_id(entity.getReport_board().getClient_id())
				.report_board_no(entity.getReport().getBoard_no())
				.report_comment(entity.getReport_comment())
				.build();
	}
}
