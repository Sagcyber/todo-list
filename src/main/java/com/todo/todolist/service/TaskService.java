package com.todo.todolist.service;

import com.todo.todolist.dto.request.TaskUpdateRequest;
import com.todo.todolist.entity.Task;

import java.util.List;

public interface TaskService {
    
    Task create(Task task, Long userId, Long categoryId);
    
    Task getById(Long id);
    
    List<Task> getAll();
    
    List<Task> getByCategory(Long categoryId);
    
    Task update(Long id, TaskUpdateRequest updateRequest);
    
    void delete(Long id);
}
