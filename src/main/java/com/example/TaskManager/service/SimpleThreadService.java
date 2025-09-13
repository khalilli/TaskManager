package com.example.TaskManager.service;

import org.springframework.stereotype.Service;

@Service
public class SimpleThreadService {
    public void runInNewThread(String message){
        Thread thread = new Thread(() ->
                System.out.println("Running in a separate thread: " + message));

        thread.start();
    }
}
