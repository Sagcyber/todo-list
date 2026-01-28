package com.todo.todolist.controller;

import com.todo.todolist.dto.request.TaskCreateRequest;
import com.todo.todolist.dto.request.TaskUpdateRequest;
import com.todo.todolist.dto.response.TaskResponse;
import com.todo.todolist.entity.TaskType;
import com.todo.todolist.mapper.TaskMapper;
import com.todo.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(
            @RequestBody TaskCreateRequest request,
            @RequestParam Long userId,
            @RequestParam(required = false) Long categoryId
    ) {
        return taskMapper.toResponse(
                taskService.create(
                        taskMapper.toEntity(request),
                        userId,
                        categoryId
                )
        );
    }
    
    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable Long id) {
        return taskMapper.toResponse(taskService.getById(id));
    }
    
    @GetMapping
    public List<TaskResponse> getAll() {
        return taskService.getAll()
                          .stream()
                          .map(taskMapper::toResponse)
                          .toList();
    }
    
    @GetMapping("/by-category/{categoryId}")
    public List<TaskResponse> getByCategory(@PathVariable Long categoryId) {
        return taskService.getByCategory(categoryId)
                          .stream()
                          .map(taskMapper::toResponse)
                          .toList();
    }
    
    @GetMapping("/by-type/{type}")
    public List<TaskResponse> getByType(@PathVariable TaskType type) {
        return taskService.getByType(type)
                          .stream()
                          .map(taskMapper::toResponse)
                          .toList();
    }
    
    @PutMapping("/{id}")
    public TaskResponse update(
            @PathVariable Long id,
            @RequestBody TaskUpdateRequest request
    ) {
        return taskMapper.toResponse(taskService.update(id, request));
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}