package com.anbd.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer>{
	@Query("select c.category_no from CategoryEntity c where c.category_name = :category ")
	Integer findCategory_no(@Param("category") String category);
}
