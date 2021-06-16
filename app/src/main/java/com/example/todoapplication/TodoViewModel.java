package com.example.todoapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;

import java.util.List;

public class TodoViewModel extends ViewModel {

    private TodoRepo repo = new TodoRepo();

    public TodoViewModel() {

    }

    public LiveData<List<Todo>> getTodos() {
        return repo.getTodos();
    }

    public Task<Void> addTodo(Todo todo) {
        return repo.addTodo(todo);
    }

    public void checkTodo(String id) {
        repo.checkTodo(id);
    }
}
