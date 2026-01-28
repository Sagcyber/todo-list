package com.todo.todolist.controller;

import com.todo.todolist.dto.response.CategoryResponse;
import com.todo.todolist.entity.Category;
import com.todo.todolist.mapper.CategoryMapper;
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
    private final CategoryMapper categoryMapper;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@RequestBody Category category) {
        return categoryMapper.toResponse(categoryService.create(category));
    }
    
    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable Long id) {
        return categoryMapper.toResponse(categoryService.getById(id));
    }
    
    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryService.getAll()
                              .stream()
                              .map(categoryMapper::toResponse)
                              .toList();
    }
    
    @PutMapping("/{id}")
    public CategoryResponse update(
            @PathVariable Long id,
            @RequestBody Category category
    ) {
        return categoryMapper.toResponse(categoryService.update(id, category));
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}