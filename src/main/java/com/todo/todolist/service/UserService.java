package com.todo.todolist.service;

import com.todo.todolist.dto.request.UserUpdateRequest;
import com.todo.todolist.entity.User;

import java.util.List;

public interface UserService {
    
    User create(User user);
    
    User getById(Long id);
    
    List<User> getAll();
    
    User update(Long id, UserUpdateRequest request);
    
    void delete(Long id);
}
