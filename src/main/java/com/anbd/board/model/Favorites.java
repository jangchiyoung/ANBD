package com.anbd.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorites {

	private Integer favorites_no;
	private Integer favorites_product_no;
	private String favorites_client_id;
}
