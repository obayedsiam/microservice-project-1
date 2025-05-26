package com.example.UserService.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String password;
    private String email;
}
