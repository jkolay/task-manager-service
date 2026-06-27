package com.task.specification;

import com.task.entity.Task;
import com.task.entity.TaskPriority;
import com.task.entity.TaskStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


@Component
public class TaskSpecification {


    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }


    public static Specification<Task> hasPriority(TaskPriority priority) {
        return (root, query, criteriaBuilder) -> {
            if (priority == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("priority"), priority);
        };
    }


    public static Specification<Task> filterByStatusAndPriority(TaskStatus status, TaskPriority priority) {
        return Specification.where(hasStatus(status)).and(hasPriority(priority));
    }
}
