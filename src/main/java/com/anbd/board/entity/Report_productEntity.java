package com.anbd.board.entity;

import javax.persistence.Column;
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
@Table(name = "report_product")
public class Report_productEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer report_no;
	
	@ManyToOne(targetEntity = ClientEntity.class)
	private ClientEntity report_id;
	
	@ManyToOne(targetEntity = ClientEntity.class)
	private ClientEntity report_product_id;
	
	@ManyToOne(targetEntity = ProductEntity.class)
	private ProductEntity report_product_no;
	
	@Column(nullable = false)
	private String report_comment;
}
