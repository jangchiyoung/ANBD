package com.anbd.board.service;


import com.anbd.board.entity.CategoryEntity;
import com.anbd.board.entity.ClientEntity;
import com.anbd.board.entity.ProductEntity;
import com.anbd.board.model.Product;

public interface ProductService {

	default ProductEntity toEntity(Product dto) {
		ClientEntity product_seller = ClientEntity.builder().client_id(dto.getProduct_seller_client_id()).build();
		CategoryEntity product_category = CategoryEntity.builder().category_no(dto.getProduct_category_no()).build();
		return ProductEntity.builder()
				.product_no(dto.getProduct_no())
				.product_name(dto.getProduct_name())
				.product(product_category)
				.product_content(dto.getProduct_content())
				.product_price(dto.getProduct_price())
				.product_img1(dto.getProduct_img1())
				.product_img2(dto.getProduct_img2())
				.product_img3(dto.getProduct_img3())
				.product_img4(dto.getProduct_img4())
				.product_img5(dto.getProduct_img5())
				.product_seller(product_seller)
				.product_buyer_client_id(dto.getProduct_buyer_client_id())
				.product_like(dto.getProduct_like())
				.product_readcount(dto.getProduct_readcount())
				.product_status(dto.getProduct_status())
				.product_date(dto.getProduct_date())
				.product_done_date(dto.getProduct_done_date())
				.build();
	}
	
	default Product toDto(ProductEntity entity) {

		return Product.builder()
				.product_no(entity.getProduct_no())
				.product_category_no(entity.getProduct().getCategory_no())
				.product_name(entity.getProduct_name())
				.product_content(entity.getProduct_content())
				.product_price(entity.getProduct_price())
				.product_img1(entity.getProduct_img1())
				.product_img2(entity.getProduct_img2())
				.product_img3(entity.getProduct_img3())
				.product_img4(entity.getProduct_img4())
				.product_img5(entity.getProduct_img5())
				.product_seller_client_id(entity.getProduct_seller().getClient_id())
				.product_buyer_client_id(entity.getProduct_buyer_client_id())
				.product_like(entity.getProduct_like())
				.product_readcount(entity.getProduct_readcount())
				.product_status(entity.getProduct_status())
				.product_date(entity.getProduct_date())
				.product_done_date(entity.getProduct_done_date())
				.build();
	}
}
