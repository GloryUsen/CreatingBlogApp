package com.springBoot.MbakaraBlogApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.MbakaraBlogApp.dtos.CategoryDTO;
import com.springBoot.MbakaraBlogApp.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    // Build addCategory REST API method

    @SecurityRequirement(
        name = "Bearer Authentication"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Meaning only admin user can access addCategory REST API.
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO){ // So @RequestBody will extract th JSON from the request and it will convert that JSON into Java obj.
       //categoryService.addCategory(categoryDTO);
        CategoryDTO saveCategory = categoryService.addCategory(categoryDTO);
        return new ResponseEntity<>(saveCategory, HttpStatus.CREATED);
    }

    // Build Get Category Rest API method
    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable ("id") Long categoryId){
        CategoryDTO categoryDTO = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryDTO);
    }

    // Build get all Category REST API method
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Building Update Category REST API method.
    
    @PreAuthorize("hasRole('ADMIN')") // Only Admin user can access this role
    @PutMapping("{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Long categoryId){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDTO, categoryId));

    }

    // Build Category REST API method
    @PreAuthorize("hasRole('ADMIN')") // Here, only adminUser can able to access this delete
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory (@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId); // this means delete the category obj from db
        return ResponseEntity.ok("Category deleted successfully!."); // then return success message to the client
        
    }
}
