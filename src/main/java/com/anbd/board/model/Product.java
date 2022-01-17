package com.anbd.board.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	
	private Integer product_no;
	private Integer product_category_no;
	private String product_name;
	private String product_content;
	private Integer product_price;
	private String product_img1;
	private String product_img2;
	private String product_img3;
	private String product_img4;
	private String product_img5;
	private String product_seller_client_id;
	private String product_buyer_client_id;
	private Integer product_like;
	private Integer product_readcount;
	private String product_status;
	private LocalDateTime product_date;
	private Timestamp product_done_date;
}
