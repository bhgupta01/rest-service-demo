package garg.bhawana.rest_service_demo;

public class InvalidTaskStatusException extends RuntimeException {
    public InvalidTaskStatusException() {
        super("Task status should match one of the acceptable values " + TaskStatus.displayValues());
    }
}
