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
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TaskMapper taskMapper;
    
    @Override
    public Task create(Task task, Long userId, Long categoryId) {
        
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> {
                                      log.warn("User not found while creating task: userId={}", userId);
                                      return new EntityNotFoundException("User not found with id: " + userId);
                                  });
        
        task.setUser(user);
        
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                                                  .orElseThrow(() -> {
                                                      log.warn("Category not found while creating task: categoryId={}", categoryId);
                                                      return new EntityNotFoundException("Category not found with id: " + categoryId);
                                                  });
            task.setCategory(category);
        }
        
        Task savedTask = taskRepository.save(task);
        
        log.info(
                "Task created: id={}, title='{}', userId={}, categoryId={}",
                savedTask.getId(),
                savedTask.getTitle(),
                userId,
                categoryId
        );
        
        return savedTask;
    }
    
    @Cacheable(value = "tasks", key = "#id")
    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id)
                             .orElseThrow(() -> {
                                 log.warn("Task not found: id={}", id);
                                 return new EntityNotFoundException("Task not found with id: " + id);
                             });
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
        
        Task updatedTask = taskRepository.save(existingTask);
        
        log.info(
                "Task updated: id={}, newTitle='{}'",
                updatedTask.getId(),
                updatedTask.getTitle()
        );
        
        return updatedTask;
    }
    
    @CacheEvict(value = "tasks", key = "#id")
    @Override
    public void delete(Long id) {
        
        Task task = getById(id);
        taskRepository.delete(task);
        
        log.info("Task deleted: id={}", id);
    }
}
