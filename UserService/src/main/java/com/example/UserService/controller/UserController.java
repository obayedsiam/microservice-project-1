package com.example.UserService.controller;

import com.example.UserService.dto.UserDto;
import com.example.UserService.enums.RoleEnum;
import com.example.UserService.enums.SortType;
import com.example.UserService.enums.UserSortBy;
import com.example.UserService.response.ApiResponse;
import com.example.UserService.response.UserResponse;
import com.example.UserService.service.UserService;
import com.example.UserService.util.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v3/api/users")
@Tag(name = "User APIs", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto dto) {
        UserResponse response = userService.save(dto);
        return ResponseEntity.ok(ApiResponse.success(Constants.CREATE_SUCCESS_MESSAGE, response));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDto dto) {
        UserResponse response = userService.save(dto);
        return ResponseEntity.ok(ApiResponse.success(Constants.UPDATE_SUCCESS_MESSAGE, response));
    }

    @GetMapping("")
    public ResponseEntity<?> findById(@RequestParam Long id) {
        UserResponse response = userService.findById(id);
        return ResponseEntity.ok(ApiResponse.success("user found successfully", response));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam Long id) {
        userService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(Constants.DELETE_SUCCESS_MESSAGE));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "USER_ID") UserSortBy sortBy,
                                         @RequestParam(required = false) RoleEnum userType,
                                         @RequestParam(required = false) SortType sortDirection,
                                         @RequestParam(defaultValue = "") String search) throws BadRequestException {
        return ResponseEntity.ok(userService.getList(size, page, sortBy.name(), sortDirection.name(), userType, search));
    }

}
