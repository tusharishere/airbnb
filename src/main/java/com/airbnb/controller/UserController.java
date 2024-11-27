package com.airbnb.controller;

import com.airbnb.entity.User;
import com.airbnb.payload.UserDto;
import com.airbnb.repository.UserRepository;
import com.airbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
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
        String encryptedPw = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(5));
        userDto.setPassword(encryptedPw);
        userDto.setRole("ROLE_USER");
        User savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/signup-property-Owner")
    public ResponseEntity<?> createPropertyOwnerUser(@RequestBody UserDto userDto) {
        Optional<User> byUsername = userRepository.findByUsername(userDto.getUsername());
        if(byUsername.isPresent()){
            return new ResponseEntity<>("Username already exists",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> byEmail = userRepository.findByEmail(userDto.getEmail());
        if(byEmail.isPresent()){
            return new ResponseEntity<>("Email already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String encryptedPw = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(5));
        userDto.setPassword(encryptedPw);
        userDto.setRole("ROLE_OWNER");
        User savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<UserDto>>getAllUsers() {
        List<UserDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid")
    public ResponseEntity<String> deleteUser(@RequestParam Long id){
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto userDto){
        User updatedUser = userService.updateUser(id, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


}
