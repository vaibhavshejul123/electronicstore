package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dto.CategoryDto;
import com.bikkadit.electronic.store.dto.PageableResponse;

public interface CategoryServiceI {


    CategoryDto createCategory(CategoryDto categoryDto);


    CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);


    void deleteCategory(String categoryId);


    PageableResponse<CategoryDto> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


    CategoryDto getCategoryById(String categoryId);


    PageableResponse<CategoryDto> searchCategoryByTitleKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


}
