package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.config.AppConstant;
import com.bikkadit.electronic.store.dto.ApiResponse;
import com.bikkadit.electronic.store.dto.CategoryDto;
import com.bikkadit.electronic.store.dto.ImageResponse;
import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.exception.FileNotFoundException;
import com.bikkadit.electronic.store.repository.CategoryRepository;
import com.bikkadit.electronic.store.service.CategoryServiceI;
import com.bikkadit.electronic.store.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(AppConstant.CATEGORY_URL)
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryServiceI categoryServiceI;

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductServiceI productServiceI;

    @Value("${category.profile.image.path}")
    private String imagePath;

    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(@Valid @RequestBody ProductDto productDto, @PathVariable String categoryId) {
        logger.info("Api createProductWithCategory request started");
        ProductDto product = this.productServiceI.createProductWithCategory(productDto, categoryId);
        logger.info("Api createProductWithCategory request ended with response : {}", HttpStatus.CREATED);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Api createNewUser request started");
        CategoryDto category = this.categoryServiceI.createCategory(categoryDto);
        logger.info("Api createNewUser request ended with response : {}", HttpStatus.CREATED);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> assignCategoryToProduct(@PathVariable String categoryId, @PathVariable String productId){
        ProductDto productDto = this.productServiceI.assignCategoryToProduct(categoryId, productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    /**
     * @return  products of given category
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageResponse<ProductDto>> getProductWithCategory( @PathVariable String categoryId,
                                                                            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                                            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
                                                                            @RequestParam(name = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ){
        PageResponse<ProductDto> pageResponse = this.productServiceI.getProductsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        logger.info("Api updateUser request started for user with userId : {}", categoryId);
        CategoryDto updateCategory = this.categoryServiceI.updateCategory(categoryDto, categoryId);
        logger.info("Api updateUser request ended with response : {}", HttpStatus.OK);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId) {
        logger.info("Api deleteUser request for single user with userId : {}", categoryId);
        this.categoryServiceI.deleteCategory(categoryId);
        ApiResponse apiResponse = ApiResponse.builder()
                .msg("Category" + AppConstant.DELETE_MSG)
                .success(true)
                .status(HttpStatus.OK).build();
        logger.info("Api deleteUser request ended with response : {}", HttpStatus.OK);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir)
    {
        logger.info("Api getAllUsers request started");
        PageableResponse<CategoryDto> pageResponse = this.categoryServiceI.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Api getAllUsers request ended with response : {}", HttpStatus.OK);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId) {
        logger.info("Api getUserById request for User with userId  : {}", categoryId);
        CategoryDto categoryDto = this.categoryServiceI.getCategoryById(categoryId);
        logger.info("Api getUserById request ended with response : {}", HttpStatus.OK);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<PageableResponse<CategoryDto>> searchCategoryByKeyword(@PathVariable String keyword,
                                                                             @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                             @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                                             @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
                                                                             @RequestParam(name = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir)
    {
        logger.info("Api getUserByNameContaining request for User with keyword : {}", keyword);
        PageableResponse<CategoryDto> pageResponse = this.categoryServiceI.searchCategoryByTitleKeyword(keyword, pageNumber, pageSize, sortBy, sortDir);
        logger.info("Api getUserByNameContaining request ended with response : {}", HttpStatus.OK);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @PostMapping("/upload/images/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(
            @RequestParam("categoryImage") MultipartFile categoryImage,
            @PathVariable String categoryId) throws IOException
    {
        logger.info("Api uploadUserImage request with image : {}", categoryImage);
        CategoryDto categoryDto = this.categoryServiceI.getCategoryById(categoryId);
        logger.info("user found with userId : {}", categoryId);
        String uploadCategoryImage = this.fileService.uploadImage(imagePath, categoryImage);
        logger.info("user image successfully upload on server!");
        categoryDto.setCoverImage(uploadCategoryImage);
        this.categoryServiceI.updateCategory(categoryDto, categoryId);
        logger.info("user image successfully saved in the database!");
        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(uploadCategoryImage).msg("Category" + AppConstant.IMAGE_MESSAGE)
                .success(true).status(HttpStatus.CREATED).build();
        logger.info("Api uploadUserImage request ended with response : {}", HttpStatus.CREATED);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/images/{categoryId}")
    public void serveCategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws FileNotFoundException {
        logger.info("Api serveUserImage request started with input response : {}", response);
        CategoryDto categoryDto = this.categoryServiceI.getCategoryById(categoryId);
        logger.info("get user image {}", categoryDto.getCoverImage());
        try {
            InputStream serveImage = this.fileService.getResource(imagePath, categoryDto.getCoverImage());
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(serveImage, response.getOutputStream());
        } catch (IOException ex) {
            logger.info("FileNotFoundException encounter");
            throw new FileNotFoundException(categoryId);
        }
        logger.info("Api serveUserImage request ended with input response : {}", response);
    }
}
