package com.anbd.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

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
@Table(name = "chat")
public class ChatEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer chat_no;
	
	@ManyToOne(targetEntity = ProductEntity.class)
	private ProductEntity chat;
	
	@Column(nullable = false)
	private String chat_message;
	
	@ManyToOne(targetEntity = ClientEntity.class)
	private ClientEntity chat_send;
	
	@ManyToOne(targetEntity = ClientEntity.class)
	private ClientEntity chat_receive;
	
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime chat_date;
	
	@Column(nullable = false)
	private Integer chat_status;
	

}
