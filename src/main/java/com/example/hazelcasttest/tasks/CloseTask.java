package com.example.hazelcasttest.tasks;

import com.example.hazelcasttest.TaskManager;
import java.io.Serializable;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class CloseTask implements Serializable, Runnable, ApplicationContextAware {

    private String key;
    private static ApplicationContext applicationContext;

    public CloseTask() {
    }

    public CloseTask(String key) {
        this.key = key;
    }

    @Override
    public void run() {
        TaskManager taskManager = applicationContext.getBean(TaskManager.class);
        taskManager.cancelTask(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext123) throws BeansException {
        applicationContext = applicationContext123;
    }
}
