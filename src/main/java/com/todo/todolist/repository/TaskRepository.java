package com.todo.todolist.repository;

import com.todo.todolist.entity.Task;
import com.todo.todolist.entity.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByUserId(Long userId);
    
    List<Task> findByCategoryId(Long categoryId);
    
    List<Task> findByType(TaskType type);
    
    List<Task> findByUserIdAndCompleted(Long userId, boolean completed);
}
