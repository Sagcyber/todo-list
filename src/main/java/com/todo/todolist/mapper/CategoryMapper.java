package com.todo.todolist.mapper;

import com.todo.todolist.dto.response.CategoryResponse;
import com.todo.todolist.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    
    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                               .id(category.getId())
                               .name(category.getName())
                               .build();
    }
}