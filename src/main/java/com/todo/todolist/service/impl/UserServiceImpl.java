package com.todo.todolist.service.impl;

import com.todo.todolist.dto.request.UserUpdateRequest;
import com.todo.todolist.entity.User;
import com.todo.todolist.exception.EntityNotFoundException;
import com.todo.todolist.mapper.UserMapper;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    @Override
    public User create(User user) {
        User savedUser = userRepository.save(user);
        
        log.info(
                "User created: id={}, username='{}'",
                savedUser.getId(),
                savedUser.getUsername()
        );
        
        return savedUser;
    }
    
    @Override
    @Cacheable(value = "users", key = "#id")
    public User getById(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> {
                                 log.warn("User not found: id={}", id);
                                 return new EntityNotFoundException("User not found with id: " + id);
                             });
    }
    
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
    
    @Override
    @CacheEvict(value = "users", key = "#id")
    public User update(Long id, UserUpdateRequest request) {
        
        User existingUser = getById(id);
        userMapper.updateEntity(existingUser, request);
        
        User savedUser = userRepository.save(existingUser);
        
        log.info(
                "User updated: id={}, username='{}'",
                savedUser.getId(),
                savedUser.getUsername()
        );
        
        return savedUser;
    }
    
    @Override
    @CacheEvict(value = "users", key = "#id")
    public void delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
        
        log.info("User deleted: id={}", id);
    }
}