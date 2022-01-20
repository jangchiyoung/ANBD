package com.anbd.board.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Data
@ToString
@Table(name = "product")
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer product_no;
	
	@ManyToOne(targetEntity = CategoryEntity.class)
	private CategoryEntity product;
	
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
	
	@Column(nullable = true)
	private String product_buyer_client_id;
	
	@Column
	private Integer product_like;
	
	@Column
	private Integer product_readcount;
	
	@Column
	private String product_status;
	
	@CreatedDate
	@Column
	private LocalDateTime product_date;
	
	@CreatedDate
	@Column
	private Timestamp product_done_date;
	
}
