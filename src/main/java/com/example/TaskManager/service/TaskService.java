package com.example.TaskManager.service;

import com.example.TaskManager.controller.dto.TaskCreateDto;
import com.example.TaskManager.controller.dto.TaskResponseDto;
import com.example.TaskManager.controller.dto.TaskUpdateDto;
import com.example.TaskManager.exceptions.TaskNotFoundException;
import com.example.TaskManager.model.Task;
import com.example.TaskManager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTaskById(int id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not find with id " + id));
    }

    @Transactional
    public TaskResponseDto addTask(TaskCreateDto taskDto){
        Task task = new Task();
        task.setTopic(taskDto.getTopic());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.getCompleted());

        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    @Transactional
    public void updateTask(int id, TaskUpdateDto newTask){
        Task old_task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not find with id " + id));
        old_task.setDescription(newTask.getDescription());
        old_task.setCompleted(newTask.getCompleted());
        taskRepository.save(old_task);
    }

    @Transactional
    public void patchUpdateTask(int id, TaskUpdateDto task){
        Task old_task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException(("Task not find with id " + id)));

        if(task.getDescription() != null){
            old_task.setDescription(task.getDescription());
        }

        if(task.getCompleted() != null){
            old_task.setCompleted(task.getCompleted());
        }

        taskRepository.save(old_task);
    }

    @Transactional
    public void deleteTask(int id){
        taskRepository.deleteById(id);
    }

    private TaskResponseDto toResponse(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTopic(),
                task.getDescription(),
                task.getCompleted()
        );
    }
}
