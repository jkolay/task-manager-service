package com.task.dto;

import com.task.entity.TaskPriority;
import com.task.entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Task response payload")
public class TaskResponse {

    @Schema(description = "Unique task identifier (UUID)", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Task title", example = "Implement login feature")
    private String title;

    @Schema(description = "Task description", example = "Support OAuth2 and basic auth flows")
    private String description;

    @Schema(description = "Current status", example = "OPEN")
    private TaskStatus status;

    @Schema(description = "Priority level", example = "HIGH")
    private TaskPriority priority;

    @Schema(description = "Creation timestamp")
    private Instant createdAt;

    @Schema(description = "Last update timestamp")
    private Instant updatedAt;
}