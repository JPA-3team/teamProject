package com.ohgiraffers.springdatajpa.menu;

import com.ohgiraffers.springdatajpa.menu.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.menu.service.MenuService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Chap06SpringDataJpaApplicationTests {


    @Autowired
    private MenuService menuService;
    @Autowired
    private EntityManager entityManager;


    @Test
    void selectAll_테스트() {

        //when

        List<MenuDTO> menuList = menuService.findMenuList();


        //then
        assertNotNull(menuList);
        for (MenuDTO m : menuList) {
            System.out.println(m);
        }

        System.out.println("menuList = " + menuList);
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
    @Test
    @Transactional
    void registNewMenu_테스트() {

        //given
        MenuDTO newMenu = new MenuDTO(300,"똥멍청이바보볶음밥",3000,1,"Y");

        System.out.println("newMenu = " + newMenu);

        //when
        menuService.registNewMenu(newMenu);;

        //then
        assertNotNull(newMenu);

        System.out.println("newMenu = " + newMenu);
    }

    @Test
    @Transactional
    void updateMenu_테스트 () {

        //given
        MenuDTO changedMenu = new MenuDTO(5,"천사엉덩이국",3000,1,"Y");

        //when
        menuService.modifyMenu(changedMenu);

        //then
        assertEquals("천사엉덩이국",changedMenu.getMenuName());
        System.out.println("changeMenu = " + changedMenu);
    }

    @Test
    @Transactional
    void deleteMenu_테스트() {

        //given
        int menuCode = 10;

        //when

        MenuDTO menu = menuService.findMenuByCode(menuCode);
        System.out.println("menu = " + menu);

        menuService.deleteMenu(menuCode);
        entityManager.flush();
        entityManager.clear();

        //then
        MenuDTO deletedMenu;

        try {
        deletedMenu = menuService.findMenuByCode(menuCode);

        } catch (IllegalArgumentException e) {
            deletedMenu = null;
            System.out.println("Transactional 로 인해 rollback 되어 삭제 되지 않으나 테스트로는 삭제 확인하였습니다. 쿼리문 확인 완료.");
        }

        assertNull(deletedMenu);
        System.out.println("deletedMenu = " + deletedMenu);
    }
}
