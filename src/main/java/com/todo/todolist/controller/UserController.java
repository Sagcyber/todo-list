package com.todo.todolist.controller;

import com.todo.todolist.dto.request.UserCreateRequest;
import com.todo.todolist.dto.request.UserUpdateRequest;
import com.todo.todolist.dto.response.UserResponse;
import com.todo.todolist.mapper.UserMapper;
import com.todo.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final UserMapper userMapper;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody UserCreateRequest request) {
        return userMapper.toResponse(
                userService.create(userMapper.toEntity(request))
        );
    }
    
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userMapper.toResponse(userService.getById(id));
    }
    
    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll()
                          .stream()
                          .map(userMapper::toResponse)
                          .toList();
    }
    
    @PutMapping("/{id}")
    public UserResponse update(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request
    ) {
        return userMapper.toResponse(
                userService.update(id, request)
        );
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}