package com.todo.todolist.mapper;

import com.todo.todolist.dto.request.TaskCreateRequest;
import com.todo.todolist.dto.request.TaskUpdateRequest;
import com.todo.todolist.dto.response.TaskResponse;
import com.todo.todolist.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    
    public Task toEntity(TaskCreateRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(
                request.getDueDate() != null
                        ? request.getDueDate().atStartOfDay()
                        : null
        );
        task.setType(request.getType()); // ← ВАЖНО
        return task;
    }
    
    public void updateEntity(Task task, TaskUpdateRequest request) {
        
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        
        if (request.getCompleted() != null) {
            task.setCompleted(request.getCompleted());
        }
        
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate().atStartOfDay());
        }
    }
    
    public TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                           .id(task.getId())
                           .title(task.getTitle())
                           .description(task.getDescription())
                           .completed(task.isCompleted())
                           .dueDate(
                                   task.getDueDate() != null
                                           ? task.getDueDate().toLocalDate()
                                           : null
                           )
                           .userId(task.getUser().getId())
                           .categoryId(
                                   task.getCategory() != null
                                           ? task.getCategory().getId()
                                           : null
                           )
                           .build();
    }
}