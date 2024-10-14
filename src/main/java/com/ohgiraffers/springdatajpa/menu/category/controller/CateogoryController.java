package com.ohgiraffers.springdatajpa.menu.category.controller;

import com.ohgiraffers.springdatajpa.menu.category.service.CategoryService;
import com.ohgiraffers.springdatajpa.menu.category.dto.CategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/category")
public class CateogoryController {

    private final CategoryService categoryService;

    @Autowired
    public CateogoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value="/list", produces="application/json; charset=UTF-8")
    @ResponseBody
    public List<CategoryDTO> findCategoryList() {

        return categoryService.findAllCategory();
    }
}
