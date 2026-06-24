package com.task.controller;



import com.task.dto.PageResponse;
import com.task.dto.TaskRequest;
import com.task.dto.TaskResponse;
import com.task.entity.TaskPriority;
import com.task.entity.TaskStatus;
import com.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
@Tag(name = "Tasks", description = "CRUD endpoints for task management")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Create a task")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        log.info("POST /tasks");
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a task by ID")
    public ResponseEntity<TaskResponse> getTask(@PathVariable UUID id) {
        log.info("GET /tasks/{}", id);
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @GetMapping
    @Operation(summary = "List tasks with optional filtering and pagination")
    public ResponseEntity<PageResponse<TaskResponse>> listTasks(
            @RequestParam(required = false)
            @Parameter(description = "Filter by status (OPEN, IN_PROGRESS, DONE)")
            TaskStatus status,

            @RequestParam(required = false)
            @Parameter(description = "Filter by priority (LOW, MEDIUM, HIGH)")
            TaskPriority priority,

            @RequestParam(defaultValue = "0")
            @Parameter(description = "0-based page index")
            int page,

            @RequestParam(defaultValue = "20")
            @Parameter(description = "Page size (max 100)")
            int size) {

        int clampedSize = Math.max(1, Math.min(size, 100));
        Pageable pageable = PageRequest.of(Math.max(page, 0), clampedSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        log.info("GET /tasks - status={}, priority={}, page={}, size={}", status, priority, page, clampedSize);
        return ResponseEntity.ok(taskService.listTasks(status, priority, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable UUID id,
            @Valid @RequestBody TaskRequest request) {
        log.info("PUT /tasks/{}", id);
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        log.info("DELETE /tasks/{}", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
