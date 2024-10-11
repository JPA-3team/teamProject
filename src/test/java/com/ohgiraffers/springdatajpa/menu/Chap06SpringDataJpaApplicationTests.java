package com.ohgiraffers.springdatajpa.menu;

import com.ohgiraffers.springdatajpa.menu.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.menu.service.MenuService;
import jakarta.persistence.Transient;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Chap06SpringDataJpaApplicationTests {

    @Autowired
    private MenuService menuService;

    @Test
    void selectAll_테스트() {

        //when

        List<MenuDTO> menuList = menuService.findMenuList();

        //then
        assertNotNull(menuList);
        for (MenuDTO m : menuList) {
            System.out.println(m);
        }

    }

    @Test
    void selectByCode_테스트() {

        //given
        int menuCode = 7;

        //when
        MenuDTO menu = menuService.findMenuByCode(menuCode);

        //then
        assertNotNull(menu);
        System.out.println("menu = " + menu);

    }

    @Test
    void selectByMenuPrice_테스트() {

        //given
        int menuPrice = 20000;

        //when
        List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);

        //then
        assertNotNull(menuList.size());

        for (MenuDTO m : menuList) {
            System.out.println("m = " + m);
        }

    }
    @Transactional
    @Test
    void registNewMenu_테스트() {

        //given
        MenuDTO newMenu = new MenuDTO(300,"똥멍청이바보볶음밥",3000,1,"Y");

        //when
        menuService.registNewMenu(newMenu);;

        //then
        assertNotNull(newMenu);
        System.out.println("newMenu = " + newMenu);
    }

    @Transactional
    @Test
    void updateMenu_테스트 () {

        //given
        MenuDTO changeMenu = new MenuDTO(5,"천사엉덩이국",3000,1,"Y");



        //when
        menuService.modifyMenu(changeMenu);

        //then
        assertEquals("천사엉덩이국",changeMenu.getMenuName());
        System.out.println("changeMenu = " + changeMenu);
    }

    @Test
    void deleteMenu_테스트(){

        //given
        int menuCode = 20;

        //when
       menuService.deleteMenu(menuCode);

        //then
        assertNull(menuService.findMenuByCode(20));

    }

}
