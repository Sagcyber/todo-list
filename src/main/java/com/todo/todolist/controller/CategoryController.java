package com.todo.todolist.controller;

import com.todo.todolist.entity.Category;
import com.todo.todolist.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody Category category) {
        return categoryService.create(category);
    }
    
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }
    
    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }
    
    @PutMapping("/{id}")
    public Category update(
            @PathVariable Long id,
            @RequestBody Category category
    ) {
        return categoryService.update(id, category);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
