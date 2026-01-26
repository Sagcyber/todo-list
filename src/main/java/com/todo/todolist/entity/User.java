package com.todo.todolist.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.scheduling.config.Task;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String username;
    
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Task> tasks;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
