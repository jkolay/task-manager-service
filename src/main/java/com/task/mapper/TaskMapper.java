package com.task.mapper;


import com.task.dto.TaskRequest;
import com.task.dto.TaskResponse;
import com.task.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequest request) {
        return Task.builder()
                .title(request.getTitle().strip())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .build();
    }

    public void updateEntity(Task task, TaskRequest request) {
        task.setTitle(request.getTitle().strip());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
    }

    public TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}