package com.todo.todolist.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskUpdateRequest {
    private String title;
    private String description;
    private Boolean completed;
    private LocalDate dueDate;
}