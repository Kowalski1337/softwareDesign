package com.ifmo.vbaydyuk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean completed;
    @Column(name = "list_id")
    private Long listId;


    public Task() {
    }

    public Task(String name, boolean completed, long listId) {
        this.name = name;
        this.completed = completed;
        this.listId = listId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", completed=" + completed +
                '}';
    }

    public boolean isCompleted() {
        return completed;
    }

    public String isCompletedStringFormat() {
        return completed ? "Yes" : "No";
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
