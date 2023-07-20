package com.bikkadit.electronic.store.service.Impl;

import com.bikkadit.electronic.store.config.AppConstant;
import com.bikkadit.electronic.store.dto.CategoryDto;
import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.exception.IllegalArgumentsException;
import com.bikkadit.electronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.Helper;
import com.bikkadit.electronic.store.model.Category;
import com.bikkadit.electronic.store.repository.CategoryRepository;
import com.bikkadit.electronic.store.service.CategoryServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class CategoryServiceImpl implements CategoryServiceI {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        String randomId = UUID.randomUUID().toString();
        Category category = this.modelMapper.map(categoryDto, Category.class);
        category.setCategoryId(randomId);
        category.setCreatedBy(categoryDto.getCreatedBy());
        Category savedCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        category.setUpdatedBy(categoryDto.getUpdatedBy());
        Category savedCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        this.categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
            Page<Category> categoryPage = this.categoryRepository.findAll(pageable);
            PageableResponse<CategoryDto> response = Helper.getPageableResponse(categoryPage, CategoryDto.class);
            return response;
        } catch (RuntimeException ex) {
            throw new IllegalArgumentsException(AppConstant.PAGE_ERROR_MSG);
        }
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> searchCategoryByTitleKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
            Page<Category> categoryPage = this.categoryRepository.findByTitleContaining(keyword, pageable);
            PageableResponse<CategoryDto> response = Helper.getPageableResponse(categoryPage, CategoryDto.class);
            return response;
        } catch (RuntimeException ex) {
            throw new IllegalArgumentsException(AppConstant.PAGE_ERROR_MSG);
        }
    }
}

