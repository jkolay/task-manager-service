package com.task.repository;


import com.task.entity.Task;
import com.task.entity.TaskPriority;
import com.task.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT t FROM Task t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:priority IS NULL OR t.priority = :priority)")
    Page<Task> findFiltered(
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            Pageable pageable
    );
}
