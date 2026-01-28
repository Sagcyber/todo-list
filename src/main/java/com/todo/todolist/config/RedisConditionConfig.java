package com.todo.todolist.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        name = "app.cache.enabled",
        havingValue = "true",
        matchIfMissing = false
)
public class RedisConditionConfig {
}
