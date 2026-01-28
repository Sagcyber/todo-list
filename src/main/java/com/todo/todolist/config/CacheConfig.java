package com.todo.todolist.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@ConditionalOnProperty(
        name = "app.cache.enabled",
        havingValue = "true"
)
public class CacheConfig {
}