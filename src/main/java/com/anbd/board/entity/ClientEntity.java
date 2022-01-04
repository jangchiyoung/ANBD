package com.anbd.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "client")
public class ClientEntity {
	
	@Id
	private String client_id;
	
	@Column(nullable =false)
	private String client_password;
	
	@Column(nullable =false)
	private String client_name;
	
	@Column(nullable =false)
	private String client_nickname;
	
	@Column(nullable =false)
	private String client_tel;
	
	@Column(nullable =false)
	private String client_email;
	
	@Column(nullable =false)
	private String client_address;
	
	@Column(nullable = true)
	private String client_img;
	
	@Column(nullable = false)
	private Integer client_status;

}
