package com.anbd.board.service;

import com.anbd.board.entity.AddressEntity;
import com.anbd.board.model.Address;

public interface AddressService {
	default AddressEntity toEntity(Address dto) {
		return AddressEntity.builder()
				.address_no(dto.getAddress_no())
				.address_name(dto.getAddress_name())
				.build();
	}
	
	default Address toDto(AddressEntity entity) {
		return Address.builder()
				.address_no(entity.getAddress_no())
				.address_name(entity.getAddress_name())
				.build();
	}
}
