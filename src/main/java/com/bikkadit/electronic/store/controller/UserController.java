package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.config.AppConstant;
import com.bikkadit.electronic.store.dto.ApiResponse;
import com.bikkadit.electronic.store.dto.ImageResponse;
import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.dto.UserDto;
import com.bikkadit.electronic.store.service.FileService;
import com.bikkadit.electronic.store.service.ServiceI;
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
import java.util.List;

@RestController
@RequestMapping(AppConstant.USER_URL)
public class UserController {

    @Autowired
    private ServiceI serviceI;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * @param userDto
     * @return userDto
     * @Auther vaibhav
     * @apiNote create user
     */

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Request sending to service for create new user");
        UserDto user = serviceI.createUser(userDto);
        logger.info("User created in database !!");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * @param id
     * @param userDto
     * @return
     * @Auther vaibhav
     * @apiNote upadate user
     */

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String id, @Valid @RequestBody UserDto userDto) {
        logger.info("Request send to serviceI for update user information {}", id);
        UserDto updateUser = serviceI.updateUser(userDto, id);

        logger.info("update user successfully !!");

        return new ResponseEntity<>(updateUser, HttpStatus.CREATED);
    }


    /**
     * @param userId
     * @return
     * @Auther vaibhav
     * @apiNote delete user
     */

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        logger.info("Request send to serviceI for delete user in database with Id {}", userId);
        serviceI.deleteUser(userId);
        ApiResponse response = ApiResponse.builder().msg(AppConstant.DELETE_MSG).success(true).status(HttpStatus.OK).build();
        logger.info(" delete user successfully !!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @return
     * @Auther vaibhav
     * @apiNote get all user
     */
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {
        logger.info("Request send to serviceI for get all user data");
        PageableResponse<UserDto> all = serviceI.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("All user found successfully");
        return new ResponseEntity<PageableResponse<UserDto>>(all, HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     * @Auther vaibhav
     * @apiNote get single user by id
     */

    @GetMapping("/userId")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable String id) {
        logger.info("Request send to the ServiceI for get single user {}", id);
        UserDto singleUser = serviceI.getSingleUser(id);
        logger.info("get single user id found Successfully !!{} ", id);
        return new ResponseEntity<>(singleUser, HttpStatus.OK);
    }

    /**
     * @param email
     * @return
     * @Auther vaibhav
     * @apiNote get user email
     */

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Request send to serviceI for get user by email {}", email);
        UserDto userByEmail = serviceI.GetUserByEmail(email);

        logger.info("get single user email found Successfully !!{}", email);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

    /**
     * @param keyword
     * @return
     * @Auther vaibhav
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        logger.info("method started !!");
        logger.info("Request send to serviceI for search user in database {}", keyword);
        List<UserDto> searchUser1 = serviceI.searchUser(keyword);
        logger.info("user search Successfully completed !!");
        return new ResponseEntity<>(searchUser1, HttpStatus.OK);
    }


    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("imageName") MultipartFile image, @PathVariable String userId) throws IOException {

        String imageName = fileService.uploadImage(imageUploadPath,image);
        UserDto user = serviceI.getSingleUser(userId);
        user.setImageName(imageName);
        UserDto userDto = serviceI.updateUser(user, userId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

        UserDto user = serviceI.getSingleUser(userId);
        logger.info("User Image Name {}", user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());


    }


}
