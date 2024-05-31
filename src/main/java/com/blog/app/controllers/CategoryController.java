package com.blog.app.controllers;

import com.blog.app.payloads.ApiResponse;
import com.blog.app.payloads.CategoriesDto;
import com.blog.app.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

// Create -
    @PostMapping("/")
    public ResponseEntity<CategoriesDto> createCategory(@Valid @RequestBody CategoriesDto categoriesDto){
        CategoriesDto category = this.categoryService.createCategory(categoriesDto);

        return new ResponseEntity<>(category, HttpStatus.CREATED);

    }
    // Update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoriesDto> updateCategory(@Valid @RequestBody CategoriesDto categoriesDto,@PathVariable Integer categoryId){
        CategoriesDto updatedCategory = this.categoryService.updateCategory(categoriesDto, categoryId);

        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }
    // get single category
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoriesDto> getSingleCategory(@Valid @PathVariable Integer categoryId){
        CategoriesDto singleCategory = this.categoryService.getSingleCategory(categoryId);

        return new ResponseEntity<>(singleCategory,HttpStatus.OK);
    }
    // Get All Category
    @GetMapping("/")
    public ResponseEntity<List<CategoriesDto>> getAllCategories(){
        List<CategoriesDto> allCategory = this.categoryService.getAllCategory();

        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }
    // Delete Category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@Valid @PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true), HttpStatus.OK);

    }
}
