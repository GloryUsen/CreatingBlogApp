package com.springBoot.MbakaraBlogApp.serviceImpl;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import org.modelmapper.ModelMapper;

import com.springBoot.MbakaraBlogApp.service.CategoryService;
import com.springBoot.MbakaraBlogApp.dtos.CategoryDTO;
import com.springBoot.MbakaraBlogApp.entity.Category;
import com.springBoot.MbakaraBlogApp.exception.ResourceNotFoundException;
import com.springBoot.MbakaraBlogApp.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {


    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper){
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public CategoryDTO addCategory(CategoryDTO categoryDTO) {

        Category category = modelMapper.map(categoryDTO, Category.class); // using model mapper to convert category data into category JP entity.
        Category saveCategory = categoryRepository.save(category);
        // Next is converting saveCategory JP into an object into category

        return modelMapper.map(saveCategory, CategoryDTO.class); // call modelMapper, then pass saveCategory as a source and categoryData as destination.
    }

    public CategoryDTO getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return modelMapper.map(category, CategoryDTO.class);
    }

    public List<CategoryDTO> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map((category) -> modelMapper.map(category, CategoryDTO.class))
        .collect(Collectors.toList());
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId){
        // If the category does not exist in the db for the given categoryId, throw ResourceNotFoundException.
        // Then you introduce the Local varaible which is Category category = categoryRepo.findById(categoryId)
        Category saveCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

                // Next is to update the category details
                saveCategory.setName(categoryDTO.getName());
                saveCategory.setDescription(categoryDTO.getDescription());
                saveCategory.setId(categoryId);

                // save the updated category back to db
                Category updatedCategory = categoryRepository.save(saveCategory);

                // so save method returns save Entity, we need to convert it to CategoryDTO and return the updated category object
                // so wee change the category entity to CategoryDTO using modelMapper

         return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    public void deleteCategory(Long categoryId) {
        // First chck if the category exist in the db or not, if not, we throw resourceNotFoundxception
        Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category); // if found, we delete the category object


    }

}
