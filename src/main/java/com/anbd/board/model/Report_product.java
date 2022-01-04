package com.anbd.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report_product {

	private Integer report_no;
	private String report_id;
	private String report_product_id;
	private Integer report_product_no;
	private String report_comment;
	
}
