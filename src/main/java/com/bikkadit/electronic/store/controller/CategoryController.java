package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.config.AppConstant;
import com.bikkadit.electronic.store.dto.*;
import com.bikkadit.electronic.store.exception.FileNotFoundException;
import com.bikkadit.electronic.store.repository.CategoryRepository;
import com.bikkadit.electronic.store.service.CategoryServiceI;
import com.bikkadit.electronic.store.service.FileService;
import com.bikkadit.electronic.store.service.ProductServiceI;
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

    /**
     * @Auther vaibhav shejul
     * @param productDto
     * @param categoryId
     * @return status code
     * @apiNote for creating a product with its category
     */
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductsDto> createProductWithCategory(@Valid @RequestBody ProductsDto productDto, @PathVariable String categoryId) {
        logger.info("Requesting a productServiceI to create product with category ");
        ProductsDto productsDto = this.productServiceI.createProductWithCategory(productDto, categoryId);
        logger.info("Request for create Product With Category ended with response : {}", HttpStatus.CREATED);
        return new ResponseEntity<>(productsDto, HttpStatus.CREATED);
    }

    /**
     * @Auther vaibhav shejul
     * @param categoryDto
     * @return status code
     * @apiNote creating a category
     */
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Api createNewUser request started");
        CategoryDto category = this.categoryServiceI.createCategory(categoryDto);
        logger.info("Api createNewUser request ended with response : {}", HttpStatus.CREATED);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * @Auther vaibhav shejul
     * @param categoryId
     * @param productId
     * @return productDto
     * @apiNote assign category to product
     */
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductsDto> assignCategoryToProduct(@PathVariable String categoryId, @PathVariable String productId) {
        ProductsDto productDto = this.productServiceI.assignCategoryToProduct(categoryId, productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    /**
     * @Auther vaibhav shejul
     * @apiNote get a product with its category
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return pageable response
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductsDto>> getProductWithCategory(@PathVariable String categoryId, @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize, @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy, @RequestParam(name = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {
        PageableResponse<ProductsDto> pageableResponse = this.productServiceI.getProductsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    /**
     * @Auther vaibhav shejul
     * @apiNote api for update a request....
     * @param categoryDto
     * @param categoryId
     * @return status code
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        logger.info("Request for updateUser with userId : {}", categoryId);
        CategoryDto updateCategory = this.categoryServiceI.updateCategory(categoryDto, categoryId);
        logger.info("Request updateUser ended with response : {}", HttpStatus.OK);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    /**
     * @Auther vaibhav shejul
     * @apiNote api for deleting a category
     * @param categoryId
     * @return status code
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId) {
        logger.info("Request for deleteUser for single user with userId : {}", categoryId);
        this.categoryServiceI.deleteCategory(categoryId);
        ApiResponse apiResponse = ApiResponse.builder().msg("Category" + AppConstant.DELETE_MSG).success(true).status(HttpStatus.OK).build();
        logger.info("Request deleteUser ended with response : {}", HttpStatus.OK);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * @Auther vaibhav shejul
     * @apiNote api for get all categories
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return status code
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize, @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy, @RequestParam(name = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {
        logger.info("request getAllUsers started");
        PageableResponse<CategoryDto> pageResponse = this.categoryServiceI.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        logger.info("request getAllUsers ended with response : {}", HttpStatus.OK);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    /**
     * @Auther vaibhav shejul
     * @param categoryId
     * @return categoryDto, status code
     * @apiNote api for get single category by its id
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId) {
        logger.info("request send to getUserById for User with userId  : {}", categoryId);
        CategoryDto categoryDto = this.categoryServiceI.getCategoryById(categoryId);
        logger.info("Request getUserById ended with response : {}", HttpStatus.OK);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    /**
     * @Auther vaibhav shejul
     * @apiNote api for serch category by keyword
     * @param keyword
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return pageResponse, status code
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<PageableResponse<CategoryDto>> searchCategoryByKeyword(@PathVariable String keyword, @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize, @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy, @RequestParam(name = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {
        logger.info("request for get UserByName Containing User with keyword : {}", keyword);
        PageableResponse<CategoryDto> pageResponse = this.categoryServiceI.searchCategoryByTitleKeyword(keyword, pageNumber, pageSize, sortBy, sortDir);
        logger.info("request get UserByName Containing ended with response : {}", HttpStatus.OK);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    /**
     * @Auther vaibhav shejul
     * @param categoryImage
     * @param categoryId
     * @return imageResponse, status code
     * @throws IOException
     * @apiNote api for uploading a category image
     */
    @PostMapping("/upload/images/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam("categoryImage") MultipartFile categoryImage, @PathVariable String categoryId) throws IOException {
        logger.info("request for uploadUserImage : {}", categoryImage);
        CategoryDto categoryDto = this.categoryServiceI.getCategoryById(categoryId);
        logger.info(" get user with userId : {}", categoryId);
        String uploadCategoryImage = this.fileService.uploadImage(imagePath, categoryImage);
        logger.info("user image successfully uploaded !!");
        categoryDto.setCoverImage(uploadCategoryImage);
        this.categoryServiceI.updateCategory(categoryDto, categoryId);
        logger.info("user image saved in the database !!");
        ImageResponse imageResponse = ImageResponse.builder().imageName(uploadCategoryImage).msg("Category" + AppConstant.IMAGE_MESSAGE).success(true).status(HttpStatus.CREATED).build();
        logger.info("Request for uploadUserImage ended with response : {}", HttpStatus.CREATED);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    /**
     * @apiNote api for serve image
     * @Auther   vaibhav shejul
     * @param categoryId
     * @param response
     * @throws FileNotFoundException
     */
    @GetMapping("/images/{categoryId}")
    public void serveCategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws FileNotFoundException {
        logger.info("request for serveUserImage started with response : {}", response);
        CategoryDto categoryDto = this.categoryServiceI.getCategoryById(categoryId);
        logger.info("get the user image {}", categoryDto.getCoverImage());
        try {
            InputStream serveImage = this.fileService.getResource(imagePath, categoryDto.getCoverImage());
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(serveImage, response.getOutputStream());
        } catch (IOException ex) {
            logger.info("FileNotFoundException encounter");
            throw new FileNotFoundException(categoryId);
        }
        logger.info("request for serveUserImage ended with response : {}", response);
    }
}
