package com.anbd.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.FavoritesEntity;

public interface FavoritesRepository extends JpaRepository<FavoritesEntity, Integer>{
	@Query("select f from FavoritesEntity f where f.favorites_product_no = :product_no")
	List<FavoritesEntity> findByProductNo(@Param("product_no") Integer product_no);
	
	@Query("select f from FavoritesEntity f where f.favorites_product_no = :product_no and f.favorites_client_id = :client_id")
	FavoritesEntity findById(@Param("product_no") Integer product_no,@Param("client_id") String client_id);
}
