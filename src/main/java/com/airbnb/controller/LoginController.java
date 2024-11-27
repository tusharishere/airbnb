package com.airbnb.controller;

import com.airbnb.payload.LoginDto;
import com.airbnb.payload.TokenDto;
import com.airbnb.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        String token = loginService.verifyLogin(loginDto);
        if(token!=null){
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid Username/Password", HttpStatus.FORBIDDEN);
        }
    }
}
