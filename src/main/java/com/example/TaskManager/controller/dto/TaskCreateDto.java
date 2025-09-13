package com.example.TaskManager.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreateDto{
    @NotBlank(message = "Topic is required")
    private String topic;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull
    private Boolean completed = false;

    @NotNull
    private LocalDate completedDate;
}
