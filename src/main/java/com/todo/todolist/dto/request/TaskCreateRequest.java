package com.todo.todolist.dto.request;

import com.todo.todolist.entity.TaskType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskCreateRequest {
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskType type;
}
