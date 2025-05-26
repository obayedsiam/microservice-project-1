package com.example.UserService.dto;

import com.example.UserService.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Schema(description = "User data transfer object")
public class UserDto {

    @Schema(description = "Unique identifier of the user", example = "0")
    private Long id;

    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Schema(description = "Password of the user", example = "Pa$$w0rd")
    private String password;

    @Schema(description = "Email address of the user", example = "john@example.com")
    private String email;

}
