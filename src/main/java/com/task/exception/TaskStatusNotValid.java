package com.task.exception;

import com.task.entity.TaskStatus;

import java.util.UUID;

public class TaskStatusNotValid extends RuntimeException{
    public TaskStatusNotValid(TaskStatus status) {
        super("Task is not valid with status:  " +status);
    }
}
