package com.ohgiraffers.springdatajpa.menu.menu.repository;

import java.util.List;
import com.ohgiraffers.springdatajpa.menu.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

//	List<Menu> findMenuByMenuPriceGreaterThan(Integer menuPrice);
	List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);

	List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);

	List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);
}
