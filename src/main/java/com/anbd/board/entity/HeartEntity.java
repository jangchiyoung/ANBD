package com.anbd.board.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name = "heart")
public class HeartEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer heart_no;
	
	@ManyToOne(targetEntity = BoardEntity.class)
	private BoardEntity heart_board_no;
	
	@ManyToOne(targetEntity = ClientEntity.class)
	private ClientEntity heart_client_id;
	
}
