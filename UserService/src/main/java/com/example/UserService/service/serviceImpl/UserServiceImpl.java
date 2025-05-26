package com.example.UserService.service.serviceImpl;

import com.example.UserService.dto.UserDto;
import com.example.UserService.entity.User;
import com.example.UserService.enums.RecordStatus;
import com.example.UserService.enums.RoleEnum;
import com.example.UserService.response.PaginatedResponse;
import com.example.UserService.response.UserResponse;
import com.example.UserService.respository.UserRepository;
import com.example.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public UserResponse save(UserDto dto) {
        User user = mapper.map(dto, User.class);
        user.setId(null);
        user = userRepository.save(user);
        return mapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse update(UserDto dto) {
        User user = mapper.map(dto, User.class);
        Optional<User> existingUser = Optional.of(userRepository.findById(dto.getId()).orElseThrow());
        user.setId(existingUser.get().getId());
        user.setUsername(existingUser.get().getUsername());
        user.setEmail(existingUser.get().getEmail());
        return mapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public void delete(Long userId) {
       User user =  userRepository.findById(userId).orElseThrow();
       user.setRecordStatus(RecordStatus.DELETED);
       userRepository.save(user);
    }

    @Override
    public UserResponse findById(Long userId) {
        return mapper.map(userRepository.findById(userId).orElseThrow(), UserResponse.class);
    }

    public PaginatedResponse<UserResponse> getList(Integer size,
                                                   Integer page,
                                                   String sortBy,
                                                   String sortDirection,
                                                   RoleEnum userType,
                                                   String search) throws BadRequestException {
        return null;
    }
}
