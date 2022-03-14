package com.anbd.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report_board {

	private Integer report_no;
	private String report_client_id;
	private String report_board_client_id;
	private Integer report_board_no;
	private String report_comment;
	
}
