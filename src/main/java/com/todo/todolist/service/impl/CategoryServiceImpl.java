package com.todo.todolist.service.impl;

import com.todo.todolist.entity.Category;
import com.todo.todolist.exception.EntityNotFoundException;
import com.todo.todolist.repository.CategoryRepository;
import com.todo.todolist.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }
    
    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                                 .orElseThrow(() ->
                                                      new EntityNotFoundException("Category not found with id: " + id));
    }
    
    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    
    @Override
    public Category update(Long id, Category updatedCategory) {
        Category existingCategory = getById(id);
        existingCategory.setName(updatedCategory.getName());
        return categoryRepository.save(existingCategory);
    }
    
    @Override
    public void delete(Long id) {
        Category category = getById(id);
        categoryRepository.delete(category);
    }
}
