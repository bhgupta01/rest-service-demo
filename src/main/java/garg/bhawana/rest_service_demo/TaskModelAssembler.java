package garg.bhawana.rest_service_demo;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

@Component
public class TaskModelAssembler implements RepresentationModelAssembler<Task, EntityModel<Task>> {

    @Override
    public EntityModel<Task> toModel(Task entity) {
        final UUID id = entity.getId();
        final String description = entity.getDescription();
        final EntityModel<Task> model = EntityModel.of(
                entity,
                linkTo(methodOn(TaskController.class).fetch(id))
                        .withSelfRel(),
                linkTo(methodOn(TaskController.class).fetch())
                        .withRel("all"));

        if(entity.getStatus().equals(TaskStatus.TODO)) {
                model.add(linkTo(methodOn(TaskController.class).update(description, TaskStatus.IN_PROGRESS, id))
                        .withRel("markInProgress"));
        } else if(entity.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                model.add(linkTo(methodOn(TaskController.class).update(description, TaskStatus.DONE, id))
                        .withRel("markDone"));
        }
        return model;
    }
}