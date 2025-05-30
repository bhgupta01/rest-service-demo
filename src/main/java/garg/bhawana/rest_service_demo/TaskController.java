package garg.bhawana.rest_service_demo;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TaskController {

    @GetMapping("/tasks")
    public List<Task> fetchTasks(@RequestParam String description) {
        return List.of(
                new Task(
                        UUID.randomUUID(),
                        description,
                        TaskStatus.TODO,
                        System.currentTimeMillis(),
                        System.currentTimeMillis()));
    }
}
