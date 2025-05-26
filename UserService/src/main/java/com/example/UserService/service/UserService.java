package com.example.UserService.service;

import com.example.UserService.dto.UserDto;
import com.example.UserService.enums.RoleEnum;
import com.example.UserService.response.PaginatedResponse;
import com.example.UserService.response.UserResponse;
import org.apache.coyote.BadRequestException;

public interface UserService {
    UserResponse save(UserDto dto);

    UserResponse update(UserDto dto);

    void delete(Long userId);

    UserResponse findById(Long userId);

    PaginatedResponse<UserResponse> getList(Integer size,
                                            Integer page,
                                            String sortBy,
                                            String sortDirection,
                                            RoleEnum userType,
                                            String search) throws BadRequestException;


}
