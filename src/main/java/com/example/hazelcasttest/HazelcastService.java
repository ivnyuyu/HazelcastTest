package com.example.hazelcasttest;

import com.example.hazelcasttest.tasks.CloseTask;
import com.example.hazelcasttest.tasks.MessagePrinter;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.map.IMap;
import jakarta.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.springframework.stereotype.Service;

import static com.example.hazelcasttest.HazelcastConfig.EXECUTOR_TEST_NAME;
import static com.example.hazelcasttest.HazelcastConfig.MAP_TEST_NAME;

@Service
public class HazelcastService {
    private final HazelcastInstance hazelcastInstance;
    private final TaskManager taskManager;
    private final ExecutorService executorService;


    private IMap<String, Integer> map;
    private IExecutorService hazelcastExecutorService;

    public HazelcastService(HazelcastInstance hazelcastInstance, TaskManager taskManager) {
        this.hazelcastInstance = hazelcastInstance;
        this.taskManager = taskManager;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @PostConstruct
    public void init() {
        this.map = hazelcastInstance.getMap(MAP_TEST_NAME);
        this.hazelcastExecutorService = hazelcastInstance.getExecutorService(EXECUTOR_TEST_NAME);
    }

    public void tryExecute() {
        hazelcastExecutorService.executeOnAllMembers(new MessagePrinter(UUID.randomUUID().toString(), 5));
    }

    public void putTest(String key, Integer value) {
        this.map.put(key, value);
    }

     public Integer getTest(String key) {
        return map.get(key);
    }

    public void runTask(String key) {
        Future<?> task = executorService.submit(new MessagePrinter(key, 100_000));
        taskManager.addTask(key, task);
    }

    public void closeTask(String key) {
        hazelcastExecutorService.executeOnAllMembers(new CloseTask(key));
    }

}
