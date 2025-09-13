package com.example.TaskManager.service;

import com.example.TaskManager.controller.dto.TaskCreateDto;
import com.example.TaskManager.controller.dto.TaskResponseDto;
import com.example.TaskManager.model.Task;
import com.example.TaskManager.repository.TaskRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("All tests related to Task Management CRUD")
public class TaskServiceTest {
    @Mock
    private NotificationService notificationService;
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskService taskService;

    private Instant createdDate;
    private Instant updatedDate;

    @BeforeEach
    void setUp(){
        createdDate = Instant.now();
        updatedDate = Instant.now();
    }

    @Test
    void getAllTasks_shouldSucceed(){
        LocalDate completedDate1 = LocalDate.parse("2025-09-05");
        Task task1 = new Task(1,
                          "topic1",
                     "description1",
                      true,
                                completedDate1,
                                createdDate,
                                updatedDate);
        LocalDate completedDate2 = LocalDate.parse("2025-09-15");
        Task task2 = new Task(2,
                "topic2",
                "description2",
                false,
                completedDate2,
                createdDate,
                updatedDate);

        List<Task> expectedTasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(expectedTasks);

        List<Task> actualTasks = taskRepository.findAll();

        assertThat(actualTasks)
                .usingRecursiveComparison()
                .ignoringFields("createdDate", "updatedDate")
                .isEqualTo(expectedTasks);


    }

    @Test
    void addTask_shouldSucceed() throws InterruptedException {
        LocalDate completedDate = LocalDate.parse("2025-09-05");
        TaskCreateDto newTask = new TaskCreateDto("topic1", "description1", true, completedDate);
        Task savedTask = new Task(1,"topic1","description1", true, completedDate, createdDate, updatedDate);
        TaskResponseDto responseTask = new TaskResponseDto(1,"topic1", "description1", true, completedDate);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponseDto createdTask = taskService.addTask(newTask);

        assertThat(createdTask).isEqualTo(responseTask);
        verify(taskRepository).save(any(Task.class));
    }
}
