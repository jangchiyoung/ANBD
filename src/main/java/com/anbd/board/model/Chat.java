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
public class Chat {

	private Integer chat_no;
	private String chat_roomid;
	private String chat_message;
	private String chat_seller_id;
	private String chat_buyer_id;
	private LocalDateTime chat_date;
	private Integer chat_status;
}
