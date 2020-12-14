package com.ifmo.vbaydyuk.service;

import com.ifmo.vbaydyuk.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> getByListId(long listId);

    void markAsComplete(long taskId) throws IllegalStateException;

    void deleteTask(long taskId) throws IllegalStateException;

    void addTask(String name, String list) throws IllegalStateException;
}
