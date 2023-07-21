package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.dto.ProductsDto;

public interface ProductServiceI {

    ProductsDto createProduct(ProductsDto productDto);


    ProductsDto createProductWithCategory(ProductsDto productDto, String categoryId);


    ProductsDto assignCategoryToProduct(String categoryId, String productId);


    ProductsDto updateProduct(ProductsDto productDto, String productId);


    void deleteProduct(String productId);


    PageableResponse<ProductsDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


    ProductsDto getProductById(String productId);


    PageableResponse<ProductsDto> getProductsByCategory(String categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


    PageableResponse<ProductsDto> getByTitleContaining(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


    PageableResponse<ProductsDto> getByLiveTrue(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}



