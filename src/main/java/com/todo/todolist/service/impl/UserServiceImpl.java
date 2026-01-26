package com.todo.todolist.service.impl;

import com.todo.todolist.entity.User;
import com.todo.todolist.exception.EntityNotFoundException;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() ->
                                                  new EntityNotFoundException("User not found with id: " + id));
    }
    
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
    
    @Override
    public User update(Long id, User updatedUser) {
        User existingUser = getById(id);
        
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        
        return userRepository.save(existingUser);
    }
    
    @Override
    public void delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }
}
