package com.todo.todolist.mapper;

import com.todo.todolist.dto.request.UserCreateRequest;
import com.todo.todolist.dto.response.UserResponse;
import com.todo.todolist.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public User toEntity(UserCreateRequest dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }
    
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                           .id(user.getId())
                           .username(user.getUsername())
                           .email(user.getEmail())
                           .build();
    }
}
