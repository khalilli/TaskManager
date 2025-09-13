package com.example.TaskManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    private String topic;

    @NotBlank
    private String description;

    @NotNull
    private Boolean completed;

    @NotNull
    private LocalDate completedDate;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant updatedDate;
}
