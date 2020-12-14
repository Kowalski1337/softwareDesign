package com.ifmo.vbaydyuk.controller;

import com.ifmo.vbaydyuk.model.Task;
import com.ifmo.vbaydyuk.model.TaskList;
import com.ifmo.vbaydyuk.service.TaskListService;
import com.ifmo.vbaydyuk.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskController {
    private final TaskService taskService;
    private final TaskListService taskListService;

    public TaskController(TaskService taskService, TaskListService taskListService) {
        this.taskService = taskService;
        this.taskListService = taskListService;
    }

    @RequestMapping(value = "/get-all-lists", method = RequestMethod.GET)
    public String getAllTasks(ModelMap map) {
        List<TaskList> taskLists = taskListService.getAllTaskLists();
        taskLists.forEach(list -> list.setTasks(taskService.getByListId(list.id)));
        prepareModelMap(map, taskLists);
        return "index";
    }

    @RequestMapping(value = "/add-list", method = RequestMethod.POST)
    public String addList(@RequestParam("name") String name) {
        taskListService.createList(name);
        return "redirect:/get-all-lists";
    }

    @RequestMapping(value = "/add-task", method = RequestMethod.POST)
    public String addTask(@RequestParam("name") String name, @RequestParam("listName") String listName) {
        taskService.addTask(name, listName);
        return "redirect:/get-all-lists";
    }

    @RequestMapping(value = "/delete-list", method = RequestMethod.POST)
    public String deleteList(@RequestParam(name = "listId") long listId) {
        taskListService.deleteList(listId);
        return "redirect:/get-all-lists";
    }

    @RequestMapping(value = "/delete-task", method = RequestMethod.POST)
    public String deleteTask(@RequestParam(name = "taskId") long taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/get-all-lists";
    }

    @RequestMapping(value = "/complete-task", method = RequestMethod.POST)
    public String completeTask(@RequestParam(name = "taskId") long taskId) {
        taskService.markAsComplete(taskId);
        return "redirect:/get-all-lists";
    }

    private void prepareModelMap(ModelMap map, List<TaskList> lists) {
        map.addAttribute("taskLists", lists);
        map.addAttribute("taskList", new TaskList());
        map.addAttribute("task", new Task());
    }
}
