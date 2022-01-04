package com.anbd.board.service;

import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.FavoritesEntity;
import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Favorites;

public interface FavoritesService {
	default FavoritesEntity toEntity(Favorites dto) {
		ProductEntity favorites_product_no = ProductEntity.builder().product_no(dto.getFavorites_product_no()).build();
		ClientEntity favorites_client_id = ClientEntity.builder().client_id(dto.getFavorites_client_id()).build();
		return FavoritesEntity.builder()
				.favorites_no(dto.getFavorites_no())
				.favorites_product_no(favorites_product_no)
				.favorites_client_id(favorites_client_id)
				.build();
	}
	
	default Favorites toDto(FavoritesEntity entity) {
		return Favorites.builder()
				.favorites_no(entity.getFavorites_no())
				.favorites_product_no(entity.getFavorites_product_no().getProduct_no())
				.favorites_client_id(entity.getFavorites_client_id().getClient_id())
				.build();
	}
}
