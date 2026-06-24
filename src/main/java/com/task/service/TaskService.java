package com.task.service;



import com.task.dto.PageResponse;
import com.task.dto.TaskRequest;
import com.task.dto.TaskResponse;
import com.task.entity.Task;
import com.task.entity.TaskPriority;
import com.task.entity.TaskStatus;
import com.task.exception.TaskNotFoundException;
import com.task.mapper.TaskMapper;
import com.task.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        log.info("Creating task with title: {}", request.getTitle());
        Task task = taskMapper.toEntity(request);
        task = taskRepository.save(task);
        log.info("Task created with id: {}", task.getId());
        return taskMapper.toResponse(task);
    }

    @Transactional(readOnly = true)
    public TaskResponse getTask(UUID id) {
        log.info("Fetching task id: {}", id);
        Task task = findOrThrow(id);
        return taskMapper.toResponse(task);
    }

    @Transactional(readOnly = true)
    public PageResponse<TaskResponse> listTasks(TaskStatus status, TaskPriority priority, Pageable pageable) {
        log.info("Listing tasks - status: {}, priority: {}, page: {}, size: {}",
                status, priority, pageable.getPageNumber(), pageable.getPageSize());
        Page<Task> page = taskRepository.findFiltered(status, priority, pageable);
        return toPageResponse(page);
    }

    @Transactional
    public TaskResponse updateTask(UUID id, TaskRequest request) {
        log.info("Updating task id: {}", id);
        Task task = findOrThrow(id);
        taskMapper.updateEntity(task, request);
        task = taskRepository.save(task);
        return taskMapper.toResponse(task);
    }

    @Transactional
    public void deleteTask(UUID id) {
        log.info("Deleting task id: {}", id);
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    private Task findOrThrow(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    private PageResponse<TaskResponse> toPageResponse(Page<Task> page) {
        return PageResponse.<TaskResponse>builder()
                .content(page.getContent().stream().map(taskMapper::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
