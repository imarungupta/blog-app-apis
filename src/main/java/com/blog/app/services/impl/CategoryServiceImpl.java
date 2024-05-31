package com.blog.app.services.impl;

import com.blog.app.entities.Category;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.CategoriesDto;
import com.blog.app.repositories.CategoryRepository;
import com.blog.app.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoriesDto createCategory(CategoriesDto categoriesDto) {

        Category category = this.modelMapper.map(categoriesDto, Category.class);
        Category addedCategory = this.categoryRepository.save(category);
        CategoriesDto categoryDto = this.modelMapper.map(addedCategory, CategoriesDto.class);

        return categoryDto;
    }
    @Override
    public CategoriesDto updateCategory(CategoriesDto categoriesDto, Integer categoryId) {
        Category categoryFromDB = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        categoryFromDB.setCategoryTitle(categoriesDto.getCategoryTitle());
        categoryFromDB.setCategoryDescription(categoriesDto.getCategoryDescription());

        Category updatedCategory = this.categoryRepository.save(categoryFromDB);

        CategoriesDto updatedCatetoryDto = this.modelMapper.map(updatedCategory, CategoriesDto.class);

        return updatedCatetoryDto;
    }

    @Override
    public List<CategoriesDto> getAllCategory() {
        List<Category> categoryList = this.categoryRepository.findAll();
        List<CategoriesDto> categorDtoyList = categoryList.stream().map(category -> this.modelMapper.map(category, CategoriesDto.class)).collect(Collectors.toList());
        return categorDtoyList;
    }

    @Override
    public CategoriesDto getSingleCategory(Integer categoryId) {
        Category categoryFromDB = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        CategoriesDto categoryDto = this.modelMapper.map(categoryFromDB, CategoriesDto.class);
        return categoryDto;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category categoryFromDB = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        this.categoryRepository.delete(categoryFromDB);
    }
}
