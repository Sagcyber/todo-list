package com.todo.todolist.dto.response;

import com.todo.todolist.entity.Task;
import com.todo.todolist.entity.TaskType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private TaskType type;
    private String description;
    private boolean completed;
    private LocalDate dueDate;
    private Long userId;
    private Long categoryId;
    
    public TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                           .id(task.getId())
                           .title(task.getTitle())
                           .description(task.getDescription())
                           .completed(task.isCompleted())
                           .type(task.getType()) // ← ВАЖНО
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
