package com.example.todoapplication;

public class CountTodo {
    private int totalTodo = 0;
    private int completedTodo = 0;

    public CountTodo() {
    }

    public CountTodo(int totalTodo, int completedTodo) {
        this.totalTodo = totalTodo;
        this.completedTodo = completedTodo;
    }

    public int getCompletedTodo() {
        return completedTodo;
    }

    public int getTotalTodo() {
        return totalTodo;
    }

    public void setCompletedTodo(int completedTodo) {
        this.completedTodo = completedTodo;
    }

    public void setTotalTodo(int totalTodo) {
        this.totalTodo = totalTodo;
    }
}
