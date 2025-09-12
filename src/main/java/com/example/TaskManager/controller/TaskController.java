package com.example.TaskManager.controller;

import com.example.TaskManager.controller.dto.TaskCreateDto;
import com.example.TaskManager.controller.dto.TaskResponseDto;
import com.example.TaskManager.controller.dto.TaskUpdateDto;
import com.example.TaskManager.model.Task;
import com.example.TaskManager.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable("id") int id){
        return  taskService.getTaskById(id);
    }

    @PostMapping("/task")
    public TaskResponseDto addTask(@Valid @RequestBody TaskCreateDto taskDto){
        return taskService.addTask(taskDto);
    }

    @PutMapping("/task/{id}")
    public void updateTask(@PathVariable("id") int id, @Valid @RequestBody TaskUpdateDto newTask){
        taskService.updateTask(id, newTask);
    }

    @PatchMapping("/task/{id}")
    public void patchUpdateTask(@PathVariable("id") int id, @Valid @RequestBody TaskUpdateDto newTask){
        taskService.patchUpdateTask(id, newTask);
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable("id") int id){
        taskService.deleteTask(id);
    }

}
