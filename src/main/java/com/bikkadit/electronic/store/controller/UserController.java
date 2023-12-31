package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.config.AppConstant;
import com.bikkadit.electronic.store.dto.ApiResponse;
import com.bikkadit.electronic.store.dto.UserDto;
import com.bikkadit.electronic.store.service.ServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstant.USER_URL)
public class UserController {

    @Autowired
    private ServiceI serviceI;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * @param userDto
     * @return userDto
     * @Auther vaibhav
     * @apiNote create user
     */

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
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
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String id, @RequestBody UserDto userDto) {
        logger.info("Request send to serviceI for update user information {}" , id);
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
        logger.info("Request send to serviceI for delete user in database with Id {}" ,userId);
        serviceI.deleteUser(userId);
        ApiResponse response = ApiResponse.builder()
                .msg(AppConstant.DELETE_MSG)
                .success(true)
                .status(HttpStatus.OK).build();
        logger.info(" delete user successfully !!");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * @return
     * @Auther vaibhav
     * @apiNote get all user
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() {
        logger.info("Request send to serviceI for get all user data");
        List<UserDto> all = serviceI.getAll();
        logger.info("All user found successfully");
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     * @Auther vaibhav
     * @apiNote get single user by id
     */

    @GetMapping("/userId")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable String id) {
        logger.info("Request send to the ServiceI for get single user {}" , id);
        UserDto singleUser = serviceI.getSingleUser(id);
        logger.info("get single user id found Successfully !!{} " ,id);
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
     * @Auther vaibhav
     * @param keyword
     * @return
     */
        @GetMapping("/serch/{keyword}")
        public ResponseEntity<List<UserDto>> searchUser (@PathVariable String keyword ){
            logger.info("method started !!");
            logger.info("Request send to serviceI for search user in database {}" ,keyword);
            List<UserDto> searchUser1 = serviceI.searchUser(keyword);
            logger.info("user search Successfully completed !!");
            return new ResponseEntity<>(searchUser1, HttpStatus.OK);
        }

    }
