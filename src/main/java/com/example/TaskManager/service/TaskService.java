package com.example.TaskManager.service;

import com.example.TaskManager.annotations.MyLog;
import com.example.TaskManager.controller.dto.TaskCreateDto;
import com.example.TaskManager.controller.dto.TaskResponseDto;
import com.example.TaskManager.controller.dto.TaskUpdateDto;
import com.example.TaskManager.exceptions.TaskNotFoundException;
import com.example.TaskManager.model.Task;
import com.example.TaskManager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final NotificationService notificationService;
    private final SimpleThreadService simpleThreadService;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(TaskService.class);

    @Cacheable("tasks")
    public List<Task> getAllTasks(){
        logger.info("All tasks are displayed.");
        return taskRepository.findAll();
    }

    public Task getTaskById(int id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not find with id " + id));
    }

    public List<Task> getOverdueTasks(LocalDate dueDate){
        List<Task> allTasks = taskRepository.findAll();
        List<Task> filteredTasks = allTasks.stream()
                                            .filter(task -> task.getCompleted() == false)
                                            .filter(task -> task.getCompletedDate().isAfter(dueDate))
                                            .collect(Collectors.toList());
        return filteredTasks;
    }

    public Map<Boolean, List<Task>> getCompletedTasks(){
        List<Task> allTasks = taskRepository.findAll();

        return allTasks.stream()
                .collect(Collectors.groupingBy(task -> task.getCompleted() == true));
    }

    public List<Task> getNTasks(int n){
        List<Task> allTasks = taskRepository.findAll();

        return allTasks.stream()
                .limit(n)
                .sorted(Comparator.comparing(Task::getCompletedDate))
                .collect(Collectors.toList());
    }

    @MyLog(message = "A new task has been added!")
    @CacheEvict(value="tasks", allEntries=true)
    @Transactional
    public TaskResponseDto addTask(TaskCreateDto taskDto) throws InterruptedException {
        logger.info("Adding new task.");
        Task task = new Task();
        task.setTopic(taskDto.getTopic());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.getCompleted());
        task.setCompletedDate(taskDto.getCompletedDate());

        Task saved = taskRepository.save(task);

        notificationService.sendTaskCreatedNotification(task, task.getId());
        simpleThreadService.runInNewThread("Task " + task.getId() + " has been created!");
        return toResponse(saved);
    }

    @CacheEvict(value="tasks", allEntries=true)
    @Transactional
    public void updateTask(int id, TaskUpdateDto newTask){
        Task old_task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not find with id " + id));
        old_task.setDescription(newTask.getDescription());
        old_task.setCompleted(newTask.getCompleted());
        taskRepository.save(old_task);
    }

    @CacheEvict(value="tasks", allEntries=true)
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

    @CacheEvict(value="tasks", allEntries=true)
    @Transactional
    public void deleteTask(int id){
        taskRepository.deleteById(id);
    }

    private TaskResponseDto toResponse(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTopic(),
                task.getDescription(),
                task.getCompleted(),
                task.getCompletedDate()
        );
    }
}
