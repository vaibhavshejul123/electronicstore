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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryServiceI {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @param categoryDto
     * @return savedCategory, status code
     * @Auther vaibhav
     * @apiNote api for create a category
     */
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        logger.info("create category method started");
        logger.info("generate random user Id {},");
        String randomId = UUID.randomUUID().toString();
        logger.info("Generated category id {}", randomId);
        Category category = this.modelMapper.map(categoryDto, Category.class);
        category.setCategoryId(randomId);
        category.setCreatedBy(categoryDto.getCreatedBy());
        Category savedCategory = this.categoryRepository.save(category);
        logger.info("create category method ended");
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    /**
     * @param categoryDto
     * @param categoryId
     * @return CategoryDto
     * @Auther vaibhav
     * @apiNote api for update category
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        logger.info("request for update Category to update data{}", categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        category.setUpdatedBy(categoryDto.getUpdatedBy());
        Category savedCategory = this.categoryRepository.save(category);
        logger.info("request ended for update category {}", savedCategory);
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    /**
     * @param categoryId
     * @Auther vaibhav
     * @apiNote api for delete category
     */
    @Override
    public void deleteCategory(String categoryId) {
        logger.info("request to delete category{}", categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        logger.info("request successfully completed !!");
        this.categoryRepository.delete(category);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return response
     * @Auther vaibhav
     * @apiNote api for get the all users data
     */
    @Override
    public PageableResponse<CategoryDto> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        try {
            logger.info("request for get all user !!");
            Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
            Page<Category> categoryPage = this.categoryRepository.findAll(pageable);
            PageableResponse<CategoryDto> response = Helper.getPageableResponse(categoryPage, CategoryDto.class);
            logger.info("request successfully completed !!");
            return response;
        } catch (RuntimeException ex) {
            throw new IllegalArgumentsException(AppConstant.PAGE_ERROR_MSG);
        }
    }

    /**
     * @param categoryId
     * @return CategoryDto
     * @Auther vaibhav
     * @apiNote api for get category by id
     */
    @Override
    public CategoryDto getCategoryById(String categoryId) {
        logger.info("Request sent to category repository to find user by its id{}", categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        logger.info("get the category by its id");
        return this.modelMapper.map(category, CategoryDto.class);
    }

    /**
     * @param keyword
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return response
     * @Auther vaibhav
     * @apiNote api for search a category
     */
    @Override
    public PageableResponse<CategoryDto> searchCategoryByTitleKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        try {
            logger.info("Request for search a category by title keyword ");
            Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
            Page<Category> categoryPage = this.categoryRepository.findByTitleContaining(keyword, pageable);
            PageableResponse<CategoryDto> response = Helper.getPageableResponse(categoryPage, CategoryDto.class);
            logger.info("Request successfully completed !!");
            return response;
        } catch (RuntimeException ex) {
            throw new IllegalArgumentsException(AppConstant.PAGE_ERROR_MSG);
        }
    }
}

