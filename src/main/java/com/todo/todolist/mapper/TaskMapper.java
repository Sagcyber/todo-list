package com.todo.todolist.mapper;

import com.todo.todolist.dto.request.TaskCreateRequest;
import com.todo.todolist.dto.request.TaskUpdateRequest;
import com.todo.todolist.dto.response.TaskResponse;
import com.todo.todolist.entity.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskMapper {
    
    public Task toEntity(TaskCreateRequest dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(
                dto.getDueDate() != null
                        ? dto.getDueDate().atStartOfDay()
                        : null
        );
        return task;
    }
    
    public void updateEntity(Task task, TaskUpdateRequest dto) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());
        task.setDueDate(
                dto.getDueDate() != null
                        ? dto.getDueDate().atStartOfDay()
                        : null
        );
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
