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
@Table(name = "comment")
public class CommentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer comment_no;
	
	@ManyToOne(targetEntity = BoardEntity.class)
	private BoardEntity comment;
	
	@Column(nullable = false)
	private String comment_client_id;
	
	@Column(nullable = false)
	private String comment_content;
	
	@CreatedDate
	private LocalDateTime comment_date;
}
