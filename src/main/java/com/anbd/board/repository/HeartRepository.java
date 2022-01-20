package com.anbd.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.HeartEntity;

public interface HeartRepository extends JpaRepository<HeartEntity, Integer>{
	@Query("select h from HeartEntity h where h.heart_board_no = :product_no")
	List<HeartEntity> findByProductNo(@Param("product_no") Integer product_no);
	
	@Query("select h from HeartEntity h where h.heart_client_id = :client_id and h.heart_board_no = :product_no")
	HeartEntity findById(@Param("client_id") String client_id, @Param("product_no") Integer product_no);
}
