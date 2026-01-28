package com.todo.todolist.mapper;

import com.todo.todolist.dto.request.UserCreateRequest;
import com.todo.todolist.dto.request.UserUpdateRequest;
import com.todo.todolist.dto.response.UserResponse;
import com.todo.todolist.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public User toEntity(UserCreateRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        return user;
    }
    
    public void updateEntity(User user, UserUpdateRequest request) {
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
    }
    
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                           .id(user.getId())
                           .username(user.getUsername())
                           .email(user.getEmail())
                           .build();
    }
}