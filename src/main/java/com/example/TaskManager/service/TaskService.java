package com.example.TaskManager.service;

import com.example.TaskManager.model.Task;
import com.example.TaskManager.repository.TaskRepository;
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

    public Optional<Task> getTaskById(int id){
        return taskRepository.findById(id);
    }

    public void updateTask(int id, Task newTask){
        Task old_task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not find with id " + id));;
        old_task.setDescription(newTask.getDescription());
        old_task.setCompleted(newTask.isCompleted());
        taskRepository.save(old_task);
    }

    public void patchUpdateTask(int id, @RequestBody Map<String, Object> updates){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException(("Task not ifnd with id " + id)));

        updates.forEach((key,value) -> {
            switch (key) {
                case "description" -> task.setDescription((String) value);
                case "completed" -> task.setCompleted((Boolean) value);
            }
        });

        taskRepository.save(task);
    }

    public void addTask(Task task){
        taskRepository.save(task);
    }

    public void deleteTask(int id){
        taskRepository.deleteById(id);
    }
}
