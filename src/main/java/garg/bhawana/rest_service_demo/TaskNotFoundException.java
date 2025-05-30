package garg.bhawana.rest_service_demo;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {
    TaskNotFoundException(UUID id) {
        super("No task found for "+id);
    }
}
