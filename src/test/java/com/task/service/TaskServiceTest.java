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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock private TaskRepository taskRepository;
    @Mock private TaskMapper taskMapper;

    @InjectMocks private TaskService taskService;

    private Task task;
    private TaskRequest request;
    private TaskResponse response;

    @BeforeEach
    void setUp() {
        UUID id = UUID.randomUUID();
        task = Task.builder()
                .id(id)
                .title("Sample Task")
                .description("A description")
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        request = new TaskRequest();
        request.setTitle("Sample Task");
        request.setDescription("A description");
        request.setStatus(TaskStatus.OPEN);
        request.setPriority(TaskPriority.MEDIUM);

        response = TaskResponse.builder()
                .id(id)
                .title("Sample Task")
                .description("A description")
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    @Test
    void createTask_success() {
        when(taskMapper.toEntity(request)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(response);

        TaskResponse result = taskService.createTask(request);

        assertThat(result.getTitle()).isEqualTo("Sample Task");
        verify(taskRepository).save(task);
    }

    @Test
    void getTask_found() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskMapper.toResponse(task)).thenReturn(response);

        TaskResponse result = taskService.getTask(task.getId());

        assertThat(result.getId()).isEqualTo(task.getId());
    }

    @Test
    void getTask_notFound_throws() {
        when(taskRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000000"))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTask(UUID.fromString("00000000-0000-0000-0000-000000000000")))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("00000000-0000-0000-0000-000000000000");
    }

    @Test
    void listTasks_returnsPagedResponse() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Task> page = new PageImpl<>(List.of(task), pageable, 1);
        when(taskRepository.findFiltered(null, null, pageable)).thenReturn(page);
        when(taskMapper.toResponse(task)).thenReturn(response);

        PageResponse<TaskResponse> result = taskService.listTasks(null, null, pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void updateTask_success() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        doNothing().when(taskMapper).updateEntity(task, request);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(response);

        TaskResponse result = taskService.updateTask(task.getId(), request);

        assertThat(result).isNotNull();
        verify(taskMapper).updateEntity(task, request);
    }

    @Test
    void deleteTask_success() {
        when(taskRepository.existsById(task.getId())).thenReturn(true);

        taskService.deleteTask(task.getId());

        verify(taskRepository).deleteById(task.getId());
    }

    @Test
    void deleteTask_notFound_throws() {
        when(taskRepository.existsById(UUID.fromString("00000000-0000-0000-0000-000000000001"))).thenReturn(false);

        assertThatThrownBy(() -> taskService.deleteTask(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
