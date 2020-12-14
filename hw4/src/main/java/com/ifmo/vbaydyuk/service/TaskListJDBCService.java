package com.ifmo.vbaydyuk.service;

import com.ifmo.vbaydyuk.model.TaskList;
import com.ifmo.vbaydyuk.repository.TaskListRepository;
import com.ifmo.vbaydyuk.repository.TaskRepository;

import java.util.List;

public class TaskListJDBCService implements TaskListService {
    private final TaskListRepository taskListRepository;
    private final TaskRepository taskRepository;

    public TaskListJDBCService(TaskListRepository taskListRepository, TaskRepository taskRepository) {
        this.taskListRepository = taskListRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskList> getAllTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public void deleteList(long listId) {
        taskRepository.deleteAll(taskRepository.findByListId(listId));
        taskListRepository.deleteById(listId);
    }

    @Override
    public void createList(String name) {
        taskListRepository.save(new TaskList(name));
    }
}
