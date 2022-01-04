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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Data
@Table(name = "product")
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer product_no;
	
	@ManyToOne(targetEntity = CategoryEntity.class)
	private CategoryEntity product_category;
	
	@Column(nullable = false)
	private String product_name;
	
	@Column(nullable = false)
	private String product_content;
	
	@Column(nullable = false)
	private Integer product_price;
	
	@Column(nullable = false)
	private String product_img1;
	
	@Column(nullable = true)
	private String product_img2;
	
	@Column(nullable = true)
	private String product_img3;
	
	@Column(nullable = true)
	private String product_img4;
	
	@Column(nullable = true)
	private String product_img5;
	
	@ManyToOne(targetEntity = ClientEntity.class)
	private ClientEntity product_seller;
	
	@ManyToOne(targetEntity = ClientEntity.class)
	private ClientEntity product_buyer;
	
	@Column(nullable = false)
	private Integer product_like;
	
	@Column(nullable = false)
	private Integer product_readcount;
	
	@Column(nullable = false)
	private String product_status;
	
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime product_date;
	
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime product_done_date;
	
}
