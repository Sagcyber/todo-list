package com.todo.todolist.controller;

import com.todo.todolist.entity.Task;
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
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(
            @RequestBody Task task,
            @RequestParam Long userId,
            @RequestParam(required = false) Long categoryId
    ) {
        return taskService.create(task, userId, categoryId);
    }
    
    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return taskService.getById(id);
    }
    
    @GetMapping
    public List<Task> getAll() {
        return taskService.getAll();
    }
    
    @GetMapping("/by-category/{categoryId}")
    public List<Task> getByCategory(@PathVariable Long categoryId) {
        return taskService.getByCategory(categoryId);
    }
    
    @PutMapping("/{id}")
    public Task update(
            @PathVariable Long id,
            @RequestBody Task task
    ) {
        return taskService.update(id, task);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
