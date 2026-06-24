package com.task.repository;


import com.task.entity.Task;
import com.task.entity.TaskPriority;
import com.task.entity.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application.properties")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        taskRepository.save(buildTask("Task A", TaskStatus.OPEN,        TaskPriority.HIGH));
        taskRepository.save(buildTask("Task B", TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM));
        taskRepository.save(buildTask("Task C", TaskStatus.DONE,        TaskPriority.LOW));
        taskRepository.save(buildTask("Task D", TaskStatus.OPEN,        TaskPriority.MEDIUM));
    }

    @Test
    void findFiltered_byStatus() {
        Page<Task> page = taskRepository.findFiltered(TaskStatus.OPEN, null, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(2);
        page.getContent().forEach(t -> assertThat(t.getStatus()).isEqualTo(TaskStatus.OPEN));
    }

    @Test
    void findFiltered_byPriority() {
        Page<Task> page = taskRepository.findFiltered(null, TaskPriority.MEDIUM, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(2);
        page.getContent().forEach(t -> assertThat(t.getPriority()).isEqualTo(TaskPriority.MEDIUM));
    }

    @Test
    void findFiltered_byStatusAndPriority() {
        Page<Task> page = taskRepository.findFiltered(TaskStatus.OPEN, TaskPriority.MEDIUM, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("Task D");
    }

    @Test
    void findFiltered_noFilter_returnsAll() {
        Page<Task> page = taskRepository.findFiltered(null, null, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(4);
    }

    private Task buildTask(String title, TaskStatus status, TaskPriority priority) {
        return Task.builder()
                .title(title)
                .status(status)
                .priority(priority)
                .build();
    }
}

