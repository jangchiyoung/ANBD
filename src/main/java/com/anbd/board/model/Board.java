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
public class Board {

	private Integer board_no;
	private String board_client_id;
	private String board_category;
	private String board_img1;
	private String board_img2;
	private String board_img3;
	private String board_content;
	private LocalDateTime board_date;
	private Integer board_count;
	private Integer board_like;
}
