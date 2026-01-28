package com.todo.todolist.service.impl;

import com.todo.todolist.dto.request.TaskUpdateRequest;
import com.todo.todolist.entity.Category;
import com.todo.todolist.entity.Task;
import com.todo.todolist.entity.TaskType;
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
    @CacheEvict(
            value = {"tasks", "tasksByCategory"},
            allEntries = true
    )
    public Task create(Task task, Long userId, Long categoryId) {
        
        User user = userRepository.findById(userId)
                                  .orElseThrow(() ->
                                                       new EntityNotFoundException("User not found with id: " + userId)
                                  );
        
        task.setUser(user);
        
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                                                  .orElseThrow(() ->
                                                                       new EntityNotFoundException("Category not found with id: " + categoryId)
                                                  );
            task.setCategory(category);
        }
        
        Task savedTask = taskRepository.save(task);
        
        log.info(
                "Task created: id={}, title='{}'",
                savedTask.getId(),
                savedTask.getTitle()
        );
        
        return savedTask;
    }
    
    @Override
    @Cacheable(
            value = "tasks",
            key = "#id",
            condition = "@environment.getProperty('app.cache.enabled') == 'true'"
    )
    public Task getById(Long id) {
        return taskRepository.findById(id)
                             .orElseThrow(() ->
                                                  new EntityNotFoundException("Task not found with id: " + id)
                             );
    }
    
    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }
    
    @Override
    @Cacheable(
            value = "tasksByCategory",
            key = "#categoryId",
            condition = "@environment.getProperty('app.cache.enabled') == 'true'"
    )
    public List<Task> getByCategory(Long categoryId) {
        return taskRepository.findByCategoryId(categoryId);
    }
    
    @Override
    public List<Task> getByType(TaskType type) {
        return taskRepository.findByType(type);
    }
    
    @Override
    @CacheEvict(
            value = {"tasks", "tasksByCategory"},
            allEntries = true
    )
    public Task update(Long id, TaskUpdateRequest updateRequest) {
        
        Task existingTask = getById(id);
        taskMapper.updateEntity(existingTask, updateRequest);
        
        Task updatedTask = taskRepository.save(existingTask);
        
        log.info(
                "Task updated: id={}, title='{}'",
                updatedTask.getId(),
                updatedTask.getTitle()
        );
        
        return updatedTask;
    }
    
    @Override
    @CacheEvict(
            value = {"tasks", "tasksByCategory"},
            allEntries = true
    )
    public void delete(Long id) {
        
        Task task = getById(id);
        taskRepository.delete(task);
        
        log.info("Task deleted: id={}", id);
    }
}