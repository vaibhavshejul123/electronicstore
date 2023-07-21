package com.bikkadit.electronic.store.service.Impl;

import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.dto.ProductsDto;
import com.bikkadit.electronic.store.service.ProductServiceI;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductServiceI {

    @Override
    public ProductsDto createProduct(ProductsDto productDto) {

        return null;
    }

    @Override
    public ProductsDto createProductWithCategory(ProductsDto productDto, String categoryId) {

        return null;
    }

    @Override
    public ProductsDto assignCategoryToProduct(String categoryId, String productId) {
        return null;
    }

    @Override
    public ProductsDto updateProduct(ProductsDto productDto, String productId) {

        return null;
    }

    @Override
    public void deleteProduct(String productId) {

    }

    @Override
    public PageableResponse<ProductsDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public ProductsDto getProductById(String productId) {
        return null;
    }

    @Override
    public PageableResponse<ProductsDto> getProductsByCategory(String categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public PageableResponse<ProductsDto> getByTitleContaining(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public PageableResponse<ProductsDto> getByLiveTrue(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }
}
