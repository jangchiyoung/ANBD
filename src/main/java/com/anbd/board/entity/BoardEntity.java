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
@Table(name = "board")
public class BoardEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer board_no;
	
	@ManyToOne(targetEntity = ClientEntity.class)
	private ClientEntity board_client_id;
	
	@ManyToOne(targetEntity = Category_boardEntity.class)
	private Category_boardEntity board_category;
	
	@Column(nullable = false)
	private String board_img1;
	
	@Column(nullable = true)
	private String board_img2;
	
	@Column(nullable = true)
	private String board_img3;
	
	@Column(nullable = false)
	private String board_content;
	
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime board_date;
	
	@Column(nullable = false)
	private Integer board_count;
	
	@Column(nullable = false)
	private Integer board_like;
	
	
}
