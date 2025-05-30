package garg.bhawana.rest_service_demo;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TaskController {

    private TaskRepository repo;

    TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/tasks")
    public Task create(@RequestBody Task task) {
        return repo.save(task);
    }

    @PutMapping("/tasks/{id}")
    public Task update(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) TaskStatus status,
            @PathVariable UUID id) {
        return repo.findById(id)
                .map(persistedTask -> {
                    if (description != null)
                        persistedTask.setDescription(description);
                    if (status != null)
                        persistedTask.setStatus(status);
                    persistedTask.setUpdatedAt(System.currentTimeMillis());
                    return repo.save(persistedTask);
                }).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @DeleteMapping("/tasks/{id}")
    public void delete(@PathVariable UUID id) {
        Objects.requireNonNull(id);
        repo.deleteById(id);
    }

    @GetMapping("/tasks/{id}")
    public Task fetch(@PathVariable UUID id) {
        Objects.requireNonNull(id);
        return repo
                .findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
    // todo: fetch tasks by status

    @GetMapping("/tasks")
    public List<Task> fetch() {
        return repo.findAll();
    }
}
