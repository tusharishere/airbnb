package com.airbnb.service;

import com.airbnb.entity.User;
import com.airbnb.payload.UserDto;
import com.airbnb.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public UserDto mapToDto(User user){
        UserDto userDto  = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public User mapToEntity(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}
