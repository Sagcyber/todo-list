package com.todo.todolist.service;

import com.todo.todolist.entity.Category;

import java.util.List;

public interface CategoryService {
    
    Category create(Category category);
    
    Category getById(Long id);
    
    List<Category> getAll();
    
    Category update(Long id, Category updatedCategory);
    
    void delete(Long id);
}
