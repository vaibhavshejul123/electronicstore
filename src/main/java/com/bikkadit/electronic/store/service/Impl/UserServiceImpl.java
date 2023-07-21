package com.bikkadit.electronic.store.service.Impl;

import com.bikkadit.electronic.store.dto.UserDto;
import com.bikkadit.electronic.store.model.User;
import com.bikkadit.electronic.store.repository.UserRepository;
import com.bikkadit.electronic.store.service.ServiceI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements ServiceI {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * @param userDto
     * @return newDto
     * @Auther vaibhav
     * @apiNote
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Create user method started!!");
        String userId = UUID.randomUUID().toString();
        logger.info("created user Id {}",userId);
        userDto.setId(userId);
        User user = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);
        logger.info("User Saved Successfully !!");
        UserDto newDto = entityToDto(savedUser);
        logger.info("User Created Successfully !!");
        return newDto;
    }


    /**
     * @param userDto
     * @param id
     * @return updatedDto
     * @Auther vaibhav
     */
    @Override
    public UserDto updateUser(UserDto userDto, String id) {

        logger.info("Request send to repository for updating buisiness logic for user");
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found by this Id !!"));
        user.setName(userDto.getName());
        user.setAbout(user.getAbout());
        user.setGender(user.getGender());
        user.setPassword(user.getPassword());
        user.setImageName(userDto.getImagename());
        User updatedUser = userRepository.save(user);
        logger.info("save Updated user successfully !!");
        UserDto updatedDto = entityToDto(updatedUser);
        logger.info("User Updated Successfully !!");
        return updatedDto;
    }

    /**
     * @param id
     * @Auther vaibhav
     */
    @Override
    public void deleteUser(String id) {
        logger.info("Request send to repository for deleting user");
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found by this Id !!"));
        logger.info("User deleted successfully !!");
        userRepository.delete(user);
    }

    /**
     * @return dtoList
     * @Auther vaibhav
     */
    @Override
    public List<UserDto> getAll() {
        logger.info("Request send to repository for find all ");
        List<User> userList = userRepository.findAll();
        List<UserDto> dtoList = userList.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        logger.info("Get All user list of all users...");
        return dtoList;
    }

    /**
     * @param id
     * @return user
     * @Auther vaibhav
     */
    @Override
    public UserDto getSingleUser(String id) {
        logger.info("Request send to repository to get single use by its id");
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usernot found by this id"));
        logger.info("successfully get the single user data{}", id);
        return entityToDto(user);
    }

    /**
     * @param email
     * @return user1
     * @Auther vaibhav
     */
    @Override
    public UserDto GetUserByEmail(String email) {
        logger.info("Request send to repository to get single use by its email{}", email);
        User user1 = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("UserNot Found By this email"));
        return entityToDto(user1);
    }

    /**
     * @param keyword
     * @return dtoList
     * @Auther vaibhav
     */
    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Request send to repository for searching user");
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        logger.info("user search by using keyword successfully !!");
        return dtoList;
    }

    /**
     * @param userDto
     * @return userDto
     * @Auther vaibhav
     * @apiNote convert dto to an entity
     */
    private User dtoToEntity(UserDto userDto) {

        return modelMapper.map(userDto, User.class);
    }

    /**
     * @param savedUser
     * @return savedUser
     * @Auther vaibhav
     * @apiNote convert entity to dto class
     */
    private UserDto entityToDto(User savedUser) {

        return modelMapper.map(savedUser, UserDto.class);
    }
}
