package com.example.hazelcasttest.tasks;

import java.io.Serializable;

public class MessagePrinter implements Serializable, Runnable {
    private final String taskId;
    private final int iteratorCounter;

    public MessagePrinter(String taskId, int iteratorCounter) {
        this.taskId = taskId;
        this.iteratorCounter = iteratorCounter;
    }

    @Override
    public void run() {
        for (int i = 0; i < iteratorCounter; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("%s ---- RandomNumber: %d", taskId,  i);
            System.out.println();
        }
    }
}
