package com.anbd.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.Report_productEntity;

public interface Report_productRepository extends JpaRepository<Report_productEntity, Integer>{

	@Query("select rp from Report_productEntity rp where rp.report_client_id = :report_client_id and rp.report_product.client_id = :report_product_client_id and rp.report.product_no = :report_product_no ")
	Report_productEntity Report_product(@Param("report_client_id") String report_client_id,@Param("report_product_client_id") String report_product_client_id,  @Param("report_product_no") Integer report_product_no);
}
