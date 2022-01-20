package com.anbd.board.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
	@Query("select p from ProductEntity p ORDER BY product_no DESC")
	List<ProductEntity> ProductAll();
	
	@Query("select p from ProductEntity p where p.product_no = :product_no")
	ProductEntity ProductDetail(@Param("product_no") int product_no);
	
	@Transactional
	@Modifying
	@Query(value = "update ProductEntity p set p.product_like = (select count(*) from FavoritesEntity f where f.favorites_product_no = :product_no) where p.product_no = :product_no")
	void updateLike(@Param("product_no") Integer product_no);
}
