package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dto.CategoryDto;
import com.bikkadit.electronic.store.dto.PageableResponse;

public interface CategoryServiceI {

    /**
     * @implNote create category method
     */
    CategoryDto createCategory(CategoryDto categoryDto);

    /**
     * @implNote update category method
     */
    CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);

    /**
     * @implNote delete category method
     */
    void deleteCategory(String categoryId);

    /**
     * @implNote get All category method
     */
    PageableResponse<CategoryDto> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    /**
     * @implNote get single category by id method
     */
    CategoryDto getCategoryById(String categoryId);

    /**
     * @implNote search category by keyword method
     */
    PageableResponse<CategoryDto> searchCategoryByTitleKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);






}
