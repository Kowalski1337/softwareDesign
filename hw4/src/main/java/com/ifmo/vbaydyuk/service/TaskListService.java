package com.ifmo.vbaydyuk.service;

import com.ifmo.vbaydyuk.model.TaskList;

import java.util.List;

public interface TaskListService {
    List<TaskList> getAllTaskLists();

    void deleteList(long listId);

    void createList(String name);
}
