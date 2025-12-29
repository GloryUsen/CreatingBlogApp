package com.springBoot.MbakaraBlogApp.service;

import java.util.List;

import com.springBoot.MbakaraBlogApp.dtos.CategoryDTO;
import com.springBoot.MbakaraBlogApp.dtos.UserPostDTO;

public interface CategoryService {

    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO getCategory(Long categoryId);
    List<CategoryDTO> getAllCategories();
    CategoryDTO updateCategory (CategoryDTO categoryDTO, Long categoryId);
    void deleteCategory(Long categoryId);
    
    
}
