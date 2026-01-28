package com.todo.todolist.service.impl;

import com.todo.todolist.entity.Category;
import com.todo.todolist.exception.EntityNotFoundException;
import com.todo.todolist.repository.CategoryRepository;
import com.todo.todolist.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    @Override
    public Category create(Category category) {
        
        Category savedCategory = categoryRepository.save(category);
        
        log.info(
                "Category created: id={}, name='{}'",
                savedCategory.getId(),
                savedCategory.getName()
        );
        
        return savedCategory;
    }
    
    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                                 .orElseThrow(() -> {
                                     log.warn("Category not found: id={}", id);
                                     return new EntityNotFoundException("Category not found with id: " + id);
                                 });
    }
    
    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    
    @Override
    public Category update(Long id, Category updatedCategory) {
        
        Category existingCategory = getById(id);
        existingCategory.setName(updatedCategory.getName());
        
        Category savedCategory = categoryRepository.save(existingCategory);
        
        log.info(
                "Category updated: id={}, newName='{}'",
                savedCategory.getId(),
                savedCategory.getName()
        );
        
        return savedCategory;
    }
    
    @Override
    public void delete(Long id) {
        
        Category category = getById(id);
        categoryRepository.delete(category);
        
        log.info("Category deleted: id={}", id);
    }
}
