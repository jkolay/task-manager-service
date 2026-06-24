package com.task.dto;


import com.task.entity.TaskPriority;
import com.task.entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request body for creating or updating a task")
public class TaskRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters")
    @Schema(description = "Task title (min 3 chars)", example = "Implement login feature")
    private String title;

    @Schema(description = "Optional task description", example = "Support OAuth2 and basic auth flows")
    private String description;

    @NotNull(message = "Status is required")
    @Schema(description = "Task status", example = "OPEN")
    private TaskStatus status;

    @NotNull(message = "Priority is required")
    @Schema(description = "Task priority", example = "HIGH")
    private TaskPriority priority;
}
