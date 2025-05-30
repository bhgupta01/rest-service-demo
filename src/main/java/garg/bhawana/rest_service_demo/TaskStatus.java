package garg.bhawana.rest_service_demo;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskStatus {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    String displayValue;

    TaskStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    @JsonCreator
    public static TaskStatus from(String str) {
        return Arrays.stream(TaskStatus.values())
                .filter(v -> v.displayValue.equals(str))
                .findFirst()
                .orElseThrow(() -> new InvalidTaskStatusException());
    }

    public static List<String> displayValues() {
        return Arrays.stream(TaskStatus.values())
                .map(v -> v.displayValue)
                .collect(Collectors.toList());
    }
}
