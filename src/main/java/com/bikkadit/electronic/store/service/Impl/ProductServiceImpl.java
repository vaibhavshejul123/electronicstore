package com.bikkadit.electronic.store.service.Impl;

import com.bikkadit.electronic.store.config.AppConstant;
import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.dto.ProductsDto;
import com.bikkadit.electronic.store.exception.IllegalArgumentsException;
import com.bikkadit.electronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.Helper;
import com.bikkadit.electronic.store.model.Category;
import com.bikkadit.electronic.store.model.Product;
import com.bikkadit.electronic.store.repository.CategoryRepository;
import com.bikkadit.electronic.store.repository.ProductRepository;
import com.bikkadit.electronic.store.service.ProductServiceI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductServiceI {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Logger logger1= LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * @Auther vaibhav
     * @param productDto
     * @return productsdto
     * @apiNote cteate a new product
     */
    @Override
    public ProductsDto createProduct(ProductsDto productDto) {
        logger1.info("method initializing started !!");
        String productId = UUID.randomUUID().toString();
        logger1.info("Generated a random user Id");
        Product product = this.modelMapper.map(productDto, Product.class);
        product.setId(productId);
        logger1.info("set the generated user Id {}" ,productId);
        product.setcreatedAt(new Date());
        product.setcreatedBy(productDto.getCreatedBy());
        Product savedProduct = this.productRepository.save(product);
        logger1.info("create a product ");
        return this.modelMapper.map(savedProduct, ProductsDto.class);
    }

    /**
     * @Auther vaibhav
     * @apiNote api for creating product with category
     * @param productDto
     * @param categoryId
     * @return savedproduct, productDto
     */
    @Override
    public ProductsDto createProductWithCategory(ProductsDto productDto, String categoryId) {
        logger1.info("method start for creating product with category ");
        String productId = UUID.randomUUID().toString();
        logger1.info("Generated a random id {}",productId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Product product = this.modelMapper.map(productDto, Product.class);
        product.setId(productId);
        logger1.info("set the generated id {}",productId);
        product.setcreatedBy(productDto.getCreatedBy());
        product.setCategory(category);
        Product savedProduct = this.productRepository.save(product);
        logger1.info("successfully created product with category !!");
        return this.modelMapper.map(savedProduct, ProductsDto.class);
    }

    /**
     * @Auther vaibhav
     * @apiNote api for assign category to the product
     * @param categoryId
     * @param productId
     * @return savedproduct, productDto
     */
    @Override
    public ProductsDto assignCategoryToProduct(String categoryId, String productId) {
        logger1.info("method initialising start for assign category");
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        product.setCategory(category);
        Product savedProduct = this.productRepository.save(product);
        logger1.info("method completed for assign category");
        return this.modelMapper.map(savedProduct, ProductsDto.class);
    }

    /**
     * @Auther vaibhav
     * @apiNote api for update a product
     * @param productDto
     * @param productId
     * @return savedProduct, ProductsDto
     */
    @Override
    public ProductsDto updateProduct(ProductsDto productDto, String productId) {
        logger1.info("method starts for update product");
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        product.setPrice(productDto.getPrice());
        product.setDiscount(productDto.getDiscount());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.getLive());
        product.setStock(productDto.getStock());
        product.setUpdatedBy(productDto.getUpdatedBy());
        Product savedProduct = this.productRepository.save(product);
        logger1.info("saved a product {}",savedProduct);
        logger1.info("method completed Successfully !");
        return this.modelMapper.map(savedProduct, ProductsDto.class);

    }

    /**
     * @Auther vaibhav
     * @apiNote api for delete product
     * @param productId
     */
    @Override
    public void deleteProduct(String productId) {
        logger1.info("api for deleting a product..");
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
       logger1.info("deleted a product successfully..");
        this.productRepository.delete(product);
    }

    /**
     * @Auther vaibhav
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return  pageableResponse
     */
    @Override
    public PageableResponse<ProductsDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        try {
            Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).ascending()) : (Sort.by(sortBy).descending());
            Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
            Page<Product> productPage = this.productRepository.findAll(pageable);
            PageableResponse<ProductsDto> pageableResponse = Helper.getPageableResponse(productPage, ProductsDto.class);
            return pageableResponse;
        }catch (RuntimeException ex){
            throw new IllegalArgumentsException(AppConstant.PAGE_ERROR_MSG);
        }



    }

    /**
     * @Auther vaibhav
     * @apiNote api for get a product by Id
     * @param productId
     * @return product, ProductsDto
     */
    @Override
    public ProductsDto getProductById(String productId) {
        logger1.info("request for initialising product by Id");
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
      logger1.info("completed a request to get a product");
        return this.modelMapper.map(product, ProductsDto.class);
    }

    /**
     * @Auther vaibhav
     * @apiNote api for get the product by its category
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return pageableResponse
     */
    @Override
    public PageableResponse<ProductsDto> getProductsByCategory(String categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        try {
            logger1.info("method initialising started for get product by category");
            Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
            Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
            Page<Product> productPage = this.productRepository.findByCategory(category, pageable);
            PageableResponse<ProductsDto> pageableResponse = Helper.getPageableResponse(productPage, ProductsDto.class);
           logger1.info("request completed a successfully");
            return pageableResponse;
        }catch (RuntimeException ex){
            throw new IllegalArgumentsException(AppConstant.PAGE_ERROR_MSG);
        }
    }

    /**
     * @Auther vaibhav
     * @apiNote api for get By Title Containing
     * @param subTitle
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return pageResponse
     */
    @Override
    public PageableResponse<ProductsDto> getByTitleContaining(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        try {
            logger1.info("method initialising starts for get title Containing");
            Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
            Page<Product> productPage = this.productRepository.findByTitleContaining(subTitle, pageable);
            PageableResponse<ProductsDto> pageResponse = Helper.getPageableResponse(productPage, ProductsDto.class);
            logger1.info("request completed a successfully");
            return pageResponse;
        }catch (RuntimeException ex){
            throw new IllegalArgumentsException(AppConstant.PAGE_ERROR_MSG);
        }
    }

    @Override
    public PageableResponse<ProductsDto> getByLiveTrue(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        try {
            logger1.info("method initialising starts for get Live true");
            Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
            Page<Product> productPage = this.productRepository.findByLiveTrue(pageable);
            PageableResponse<ProductsDto> pageResponse = Helper.getPageableResponse(productPage, ProductsDto.class);
            logger1.info("request completed a successfully");
            return pageResponse;
        }catch (RuntimeException ex){
            throw new IllegalArgumentsException(AppConstant.PAGE_ERROR_MSG);
        }
    }
    }
