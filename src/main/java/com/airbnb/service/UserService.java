package com.airbnb.service;

import com.airbnb.entity.User;
import com.airbnb.payload.UserDto;
import com.airbnb.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User createUser(UserDto userDto) {
        User user = mapToEntity(userDto);
        User saved = userRepository.save(user);
        return saved;
    }
    public List<UserDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        List<UserDto> allUsersDtos = allUsers.stream().map(u -> mapToDto(u)).collect(Collectors.toList());
        return allUsersDtos;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).get();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        String encryptedPw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));
        user.setPassword(encryptedPw);
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    public User findUserById(Long id) {
        User user = userRepository.findById(id).get();
        return user;
    }


    public UserDto mapToDto(User user){
        UserDto userDto  = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public User mapToEntity(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

}
