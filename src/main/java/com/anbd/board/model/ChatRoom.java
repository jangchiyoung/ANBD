package com.anbd.board.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ChatRoom {
	private int product_no;
    private String send_id;
    private String receive_id;
    private LocalDateTime date;
}
