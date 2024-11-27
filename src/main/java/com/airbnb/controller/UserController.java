package com.airbnb.controller;

import com.airbnb.entity.User;
import com.airbnb.payload.UserDto;
import com.airbnb.repository.UserRepository;
import com.airbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        Optional<User> byUsername = userRepository.findByUsername(userDto.getUsername());
        if(byUsername.isPresent()){
            return new ResponseEntity<>("Username already exists",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> byEmail = userRepository.findByEmail(userDto.getEmail());
        if(byEmail.isPresent()){
            return new ResponseEntity<>("Email already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userDto.setRole("ROLE_USER");
        User savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

}
