package com.example.hazelcasttest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import org.springframework.stereotype.Service;

@Service
public class TaskManager {
    public final ConcurrentHashMap<String, Future<?>> tasks;

    public TaskManager() {
        tasks = new ConcurrentHashMap<>();
    }

    public void addTask(String key, Future<?> value) {
        this.tasks.put(key, value);
    }

    public boolean cancelTask(String key) {
        Future<?> task = tasks.get(key);
        if (task != null) {
            task.cancel(true);
            tasks.remove(key);
            return true;
        } else {
            return false;
        }
    }
}
