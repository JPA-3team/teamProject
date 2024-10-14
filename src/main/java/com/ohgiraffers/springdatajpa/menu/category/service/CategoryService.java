package com.ohgiraffers.springdatajpa.menu.category.service;

import com.ohgiraffers.springdatajpa.menu.category.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.category.entity.Category;
import com.ohgiraffers.springdatajpa.menu.category.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryDTO> findAllCategory() {

        List<Category> categoryList = categoryRepository.findAllCategory();

        // 람다식 사용 버전
        return categoryList
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());

        // 람다식 대신 for문 사용한 버전
//		List<CategoryDTO> categoryDTOList = new ArrayList<>();
//		for (Category category : categoryList) {
//			CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
//			categoryDTOList.add(categoryDTO);
//		}
//		return categoryDTOList;
    }
}
