package com.anbd.board.service;

import com.anbd.board.entity.CategoryEntity;
import com.anbd.board.model.Category;

public interface CategoryService {
	default CategoryEntity toEntity(Category dto) {
		return CategoryEntity.builder()
				.category_no(dto.getCategory_no())
				.category_name(dto.getCategory_name())
				.build();
	}
	
	default Category toDto(CategoryEntity entity) {
		return Category.builder()
				.category_no(entity.getCategory_no())
				.category_name(entity.getCategory_name())
				.build();
	}
}
