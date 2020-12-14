package com.ifmo.vbaydyuk.repository;

import com.ifmo.vbaydyuk.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    TaskList findByName(String name);
}
