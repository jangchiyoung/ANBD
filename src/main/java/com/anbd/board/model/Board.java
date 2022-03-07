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
	private Integer board_category_no;
	private String board_img1;
	private String board_img2;
	private String board_img3;
	private String board_name;
	private String board_content;
	private String board_status;
	private LocalDateTime board_date;
	private Integer board_readcount;
	private Integer board_like;
	private String board_address;
}
