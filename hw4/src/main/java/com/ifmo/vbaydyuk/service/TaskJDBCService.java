package com.ifmo.vbaydyuk.service;

import com.ifmo.vbaydyuk.model.Task;
import com.ifmo.vbaydyuk.model.TaskList;
import com.ifmo.vbaydyuk.repository.TaskListRepository;
import com.ifmo.vbaydyuk.repository.TaskRepository;

import java.util.List;

public class TaskJDBCService implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskJDBCService(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> getByListId(long listId) {
        return taskRepository.findByListId(listId);
    }

    @Override
    public void markAsComplete(long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) throw new IllegalStateException("No task with task_id = " + taskId);
        taskRepository.updateCompleted(taskId, !task.isCompleted());
    }

    @Override
    public void deleteTask(long taskId) throws IllegalStateException {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void addTask(String name, String list) throws IllegalStateException {
        TaskList taskList = taskListRepository.findByName(list);
        long listId = taskList.id;
        taskRepository.save(new Task(name, false, listId));
    }

}
