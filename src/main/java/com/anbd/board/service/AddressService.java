package com.anbd.board.service;

import com.anbd.board.entity.AddressEntity;
import com.anbd.board.model.Address;

public interface AddressService {
	default AddressEntity toEntity(Address dto) {
		return AddressEntity.builder()
				.address_no(dto.getAddress_no())
				.address_main_name(dto.getAddress_main_name())
				.address_si_gun_name(dto.getAddress_si_gun_name())
				.address_gu_name(dto.getAddress_gu_name())
				.address_dong_mun_ri_name(dto.getAddress_dong_mun_ri_name())
				.build();
	}
	
	default Address toDto(AddressEntity entity) {
		return Address.builder()
				.address_no(entity.getAddress_no())
				.address_main_name(entity.getAddress_main_name())
				.address_si_gun_name(entity.getAddress_si_gun_name())
				.address_gu_name(entity.getAddress_gu_name())
				.address_dong_mun_ri_name(entity.getAddress_dong_mun_ri_name())
				.build();
	}
}
