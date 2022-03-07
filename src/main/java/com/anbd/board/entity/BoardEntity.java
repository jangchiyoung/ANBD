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
	private ClientEntity board_writer;
	
	@ManyToOne(targetEntity = Category_boardEntity.class)
	private Category_boardEntity board;
	@Column
	private String board_img1;
	
	@Column
	private String board_img2;
	
	@Column
	private String board_img3;
	
	@Column
	private String board_name;
	
	@Column
	private String board_content;
	
	@Column
	private String board_status;
	
	@CreatedDate
	private LocalDateTime board_date;
	
	@Column
	private Integer board_readcount;
	
	@Column
	private Integer board_like;
	
	@Column
	private String board_address;
	
}
