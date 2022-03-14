package com.anbd.board.service;

import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.ProductEntity;
import com.anbd.board.entity.Report_productEntity;
import com.anbd.board.model.Report_product;

public interface Report_ProductService {
	default Report_productEntity toEntity(Report_product dto) {
		ClientEntity report_product_client_id = ClientEntity.builder().client_id(dto.getReport_product_client_id()).build();
		ProductEntity report_product_no = ProductEntity.builder().product_no(dto.getReport_product_no()).build();
		return Report_productEntity.builder()
				.report_no(dto.getReport_no())
				.report_client_id(dto.getReport_client_id())
				.report_product(report_product_client_id)
				.report(report_product_no)
				.report_comment(dto.getReport_comment())
				.build();
	}
	
	default Report_product toDto(Report_productEntity entity) {
		return Report_product.builder()
				.report_product_no(entity.getReport_no())
				.report_client_id(entity.getReport_client_id())
				.report_product_client_id(entity.getReport_product().getClient_id())
				.report_product_no(entity.getReport().getProduct_no())
				.report_comment(entity.getReport_comment())
				.build();
	}
}
