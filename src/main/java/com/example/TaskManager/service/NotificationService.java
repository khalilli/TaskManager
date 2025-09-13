package com.example.TaskManager.service;

import com.example.TaskManager.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(TaskService.class);

    @Async
    public void sendTaskCreatedNotification(Task task, int id) throws InterruptedException {
        logger.info("Sending notification for task: " + id);
        Thread.sleep(2000);
    }
}
