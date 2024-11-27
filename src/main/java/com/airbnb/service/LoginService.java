package com.airbnb.service;

import com.airbnb.entity.User;
import com.airbnb.payload.LoginDto;
import com.airbnb.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private UserRepository userRepository;
    private JWTService jwtService;

    public LoginService(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String verifyLogin(LoginDto loginDto){
        Optional<User> byUsername = userRepository.findByUsername(loginDto.getUsername());
        if(byUsername.isPresent()){
            User user = byUsername.get();
            if(BCrypt.checkpw(loginDto.getPassword(),user.getPassword())){
                String token = jwtService.generateToken(loginDto.getUsername());
                return token;
            }
        }else{
            return null;
        }
        return null;
    }
}
