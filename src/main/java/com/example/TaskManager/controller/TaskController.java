package com.example.TaskManager.controller;

import com.example.TaskManager.model.Task;
import com.example.TaskManager.service.TaskService;
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
    public Optional<Task> getTaskById(@PathVariable("id") int id){
        return  taskService.getTaskById(id);
    }

    @PostMapping("/task")
    public void addTask(@RequestBody Task task){
        taskService.addTask(task);
    }

    @PutMapping("/task/{id}")
    public void updateTask(@PathVariable("id") int id, @RequestBody Task newTask){
        taskService.updateTask(id, newTask);
    }

    @PatchMapping("/task/{id}")
    public void patchUpdateTask(@PathVariable("id") int id, @RequestBody Map<String, Object> updates){
        taskService.patchUpdateTask(id, updates);
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable("id") int id){
        taskService.deleteTask(id);
    }

}
