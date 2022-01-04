package com.anbd.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
	
	private String client_id;
	private String client_password;
	private String client_name;
	private String client_nickname;
	private String client_tel;
	private String client_email;
	private String client_address;
	private String client_img;
	private Integer client_status;
}
