package com.anbd.board.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

	private Integer comment_no;
	private Integer comment_board_no;
	private String comment_client_id;
	private String comment_content;
	private LocalDateTime comment_date;
}
