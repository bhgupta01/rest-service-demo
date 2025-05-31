package garg.bhawana.rest_service_demo;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;


@RepositoryRestResource
public interface TaskRepository extends PagingAndSortingRepository<Task,UUID>, CrudRepository<Task, UUID> {
    List<Task> findByStatus(TaskStatus status);
}