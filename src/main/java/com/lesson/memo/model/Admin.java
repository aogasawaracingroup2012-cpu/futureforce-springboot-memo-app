package com.lesson.memo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Admin {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "姓を入力してください")
    @Column(nullable = false)
    private String lastName;
    
    @NotBlank(message = "名を入力してください")
    @Column(nullable = false)
    private String firstName;
    
    @NotBlank(message = "メールアドレスを入力してください")
    @Column(nullable = false , unique = true)
    private String email;
    
    @NotBlank(message = "パスワードを入力してください")
    @Column(nullable = false)
    @ToString.Exclude
    private String password;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}