package com.blog.app.services;

import com.blog.app.payloads.CategoriesDto;

import java.util.List;

public interface CategoryService {

    // Create Category
    CategoriesDto createCategory(CategoriesDto categoriesDto);
    // update category
    CategoriesDto updateCategory(CategoriesDto categoriesDto,Integer categoryId);
    // Get All Category
    List<CategoriesDto> getAllCategory();
    // Get Single Category
    CategoriesDto getSingleCategory(Integer categoryId);
    // Delete Category
    void deleteCategory(Integer categoryId);
}
