package com.airbnb.payload;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String email;
    private String password;
    private String name;
    private String role;
}
