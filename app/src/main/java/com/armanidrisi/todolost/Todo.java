package com.armanidrisi.todolost;

public class Todo {
    private long id;
    private String task;
    private boolean completed;

    public Todo(String task,boolean completed) {
        this.task = task;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
