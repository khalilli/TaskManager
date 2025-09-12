package com.example.TaskManager.controller.dto;

import lombok.Data;

@Data
public class TaskUpdateDto {
    private String description;
    private Boolean completed;
}
