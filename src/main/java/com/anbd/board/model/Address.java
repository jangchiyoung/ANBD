package com.anbd.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
	
	private Integer address_no;
	private String address_main_name;
	private String address_si_gun_name;
	private String address_gu_name;
	private String address_dong_mun_ri_name;
}
