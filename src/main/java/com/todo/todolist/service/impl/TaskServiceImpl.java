package com.todo.todolist.service.impl;

import com.todo.todolist.dto.request.TaskUpdateRequest;
import com.todo.todolist.entity.Category;
import com.todo.todolist.entity.Task;
import com.todo.todolist.entity.User;
import com.todo.todolist.exception.EntityNotFoundException;
import com.todo.todolist.mapper.TaskMapper;
import com.todo.todolist.repository.CategoryRepository;
import com.todo.todolist.repository.TaskRepository;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TaskMapper taskMapper;
    
    @Override
    public Task create(Task task, Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() ->
                                                       new EntityNotFoundException("User not found with id: " + userId));
        
        task.setUser(user);
        
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                                                  .orElseThrow(() ->
                                                                       new EntityNotFoundException("Category not found with id: " + categoryId));
            task.setCategory(category);
        }
        
        return taskRepository.save(task);
    }
    
    @Cacheable(value = "tasks", key = "#id")
    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id)
                             .orElseThrow(() ->
                                                  new EntityNotFoundException("Task not found with id: " + id));
    }
    
    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }
    
    @Cacheable(value = "tasksByCategory", key = "#categoryId")
    @Override
    public List<Task> getByCategory(Long categoryId) {
        return taskRepository.findByCategoryId(categoryId);
    }
    
    @CacheEvict(value = "tasks", key = "#id")
    @Override
    public Task update(Long id, TaskUpdateRequest updateRequest) {
        Task existingTask = getById(id);
        
        taskMapper.updateEntity(existingTask, updateRequest);
        
        return taskRepository.save(existingTask);
    }
    
    @CacheEvict(value = "tasks", key = "#id")
    @Override
    public void delete(Long id) {
        Task task = getById(id);
        taskRepository.delete(task);
    }
}
