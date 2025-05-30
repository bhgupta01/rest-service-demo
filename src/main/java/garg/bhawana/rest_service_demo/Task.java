package garg.bhawana.rest_service_demo;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private UUID id;
    private String description;
    private TaskStatus status;
    private Long createdAt;
    private Long updatedAt;

    // for JPA
    public Task() {
    }

    public Task(String description) {
        this.description = description;
    }

    @PrePersist
    public void beforePersisting() {
        this.status = TaskStatus.TODO;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
    }

    public Task(@JsonProperty("id") UUID id, @JsonProperty("description") String description,
            @JsonProperty("status") TaskStatus status, @JsonProperty("createdAt") long createdAt,
            @JsonProperty("updatedAt") long updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toString() {
        return String.format("[%s] %s %s", id, status.displayValue, description);
    }
}
