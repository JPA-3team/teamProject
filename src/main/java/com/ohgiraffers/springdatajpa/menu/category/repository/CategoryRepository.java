package com.ohgiraffers.springdatajpa.menu.category.repository;

import java.util.List;

import com.ohgiraffers.springdatajpa.menu.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository <Category, Integer> {

	@Query(value="SELECT c.category_code, c.category_name, c.ref_category_code " +
				 "FROM tbl_category c " +
				 "ORDER BY c.category_code ASC",
			nativeQuery = true)
	public List<Category> findAllCategory();
	
}
