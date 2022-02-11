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
	private Integer chat_product_no;
	private String chat_message;
	private String chat_send_client_id;
	private String chat_receive_client_id;
	private LocalDateTime chat_date;
	private Integer chat_status;
}
