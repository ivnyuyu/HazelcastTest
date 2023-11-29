package com.example.hazelcasttest;

import com.example.hazelcasttest.model.AddValueModel;
import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HazelcastController {
    private final HazelcastService hazelcastService;

    public HazelcastController(HazelcastService hazelcastService) {
        this.hazelcastService = hazelcastService;
    }

    @PostMapping("/saveValue")
    public void putValue(@RequestBody AddValueModel model) {
        hazelcastService.putTest(model.getKey(), model.getValue());
    }

    @GetMapping("/getValue")
    public Integer getValue(@RequestParam String key) {
        return hazelcastService.getTest(key);
    }

    @PostMapping("/runTask")
    public void runTask(@RequestParam String key) {
        hazelcastService.runTask(key);
    }

    @PostMapping("/closeTask")
    public void closeTask(@RequestParam String key) {
        hazelcastService.closeTask(key);
    }


    @PostMapping("/execute")
    public void execute() {
        hazelcastService.tryExecute();
    }
}
