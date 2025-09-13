package com.example.TaskManager.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TaskResponseDto {
    private int id;
    private String topic;
    private String description;
    private Boolean completed;
    private LocalDate completedDate;
}
