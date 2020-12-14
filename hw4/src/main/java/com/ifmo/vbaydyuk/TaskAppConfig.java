package com.ifmo.vbaydyuk;

import com.ifmo.vbaydyuk.repository.TaskListRepository;
import com.ifmo.vbaydyuk.repository.TaskRepository;
import com.ifmo.vbaydyuk.service.TaskJDBCService;
import com.ifmo.vbaydyuk.service.TaskListJDBCService;
import com.ifmo.vbaydyuk.service.TaskListService;
import com.ifmo.vbaydyuk.service.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskAppConfig {
    @Bean
    TaskService getTaskService(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        return new TaskJDBCService(taskRepository, taskListRepository);
    }

    @Bean
    TaskListService getTaskListService(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        return new TaskListJDBCService(taskListRepository, taskRepository);
    }
}
