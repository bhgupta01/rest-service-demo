package garg.bhawana.rest_service_demo;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// todo: fetch tasks by status
@RestController
public class TaskController {

    private TaskRepository repo;
    private TaskModelAssembler assembler;

    TaskController(TaskRepository repo, TaskModelAssembler assembler) {
        this.repo = repo;
        this.assembler = assembler;
    }

    @PostMapping("/tasks")
    public ResponseEntity<EntityModel<Task>> create(@RequestBody Task task) {
        EntityModel<Task> model = assembler.toModel(repo.save(task));
        return ResponseEntity
                .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(model);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<EntityModel<Task>> update(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) TaskStatus status,
            @PathVariable UUID id) {
        final Task task = repo.findById(id)
                .map(persistedTask -> {
                    if (description != null)
                        persistedTask.setDescription(description);
                    if (status != null)
                        persistedTask.setStatus(status);
                    persistedTask.setUpdatedAt(System.currentTimeMillis());
                    return repo.save(persistedTask);
                }).orElseThrow(() -> new TaskNotFoundException(id));
        final EntityModel<Task> model = assembler.toModel(task);
        return ResponseEntity
                .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(model);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Objects.requireNonNull(id);
        repo.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/tasks/{id}")
    public EntityModel<Task> fetch(@PathVariable UUID id) {
        Objects.requireNonNull(id);
        final Task task = repo
                .findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return assembler.toModel(task);
    }

    @GetMapping("/tasks")
    public CollectionModel<EntityModel<Task>> fetch() {
        List<EntityModel<Task>> result = repo.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                result,
                linkTo(methodOn(TaskController.class).fetch()).withSelfRel());
    }
}
