package com.anbd.board.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Product;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
	@Query("select p from ProductEntity p ORDER BY product_no DESC")
	List<ProductEntity> ProductAll();
	
	@Query("select p from ProductEntity p where p.product_no = :product_no")
	ProductEntity ProductDetail(@Param("product_no") int product_no);
	
	@Query("select p from ProductEntity p where p.product_no = :product_no")
	List<ProductEntity> cookieAdd(@Param("product_no") int product_no);
	
	@Query("select p from ProductEntity p where p.product_seller.client_id = :product_seller")
	List<ProductEntity> findSellerID(@Param("product_seller") String product_seller);
	
	@Query("select p from ProductEntity p where p.product_seller.client_id = :receiveId and p.product_no =:product_no")
	ProductEntity findSeller(@Param("receiveId") String receiveId, @Param("product_no")int product_no);
	
	@Query("select p from ProductEntity p where p.product_seller.client_id = :product_seller and p.product_status = :product_status")
	List<ProductEntity> findsalesList(@Param("product_seller") String product_seller, @Param("product_status") String product_status);
	
	@Query("select p from ProductEntity p where p.product_buyer_client_id = :product_buyer_client_id and p.product_status = :product_status")
	List<ProductEntity> findpurchaseList(@Param("product_buyer_client_id") String product_buyer_client_id, @Param("product_status") String product_status);
	
	@Query("select count(*) from ProductEntity p where p.product_seller.client_id = :product_seller")
	Integer ProductCnt(@Param("product_seller") String product_seller);
	
	@Query("select count(*) from ProductEntity p where p.product_seller.client_id = :product_seller and p.product_status = :product_status")
	Integer SalesCnt(@Param("product_seller") String product_seller, @Param("product_status") String product_status);
	
	@Query("select count(*) from ProductEntity p where p.product_buyer_client_id = :product_buyer_client_id and p.product_status = :product_status")
	Integer PurchaseCnt(@Param("product_buyer_client_id") String product_buyer_client_id, @Param("product_status") String product_status);
	
	@Transactional
	@Modifying
	@Query(value = "update ProductEntity p set p.product_like = (select count(*) from FavoritesEntity f where f.favorites_product_no = :product_no) where p.product_no = :product_no")
	void updateLike(@Param("product_no") Integer product_no);
	
	
	@Query("select p.product_seller.client_id from ProductEntity p where p.product_no = :product_no")
	String findSeller(@Param("product_no") int product_no);
}
