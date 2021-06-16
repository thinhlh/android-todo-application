package com.example.todoapplication;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TodoRepo {
    private MutableLiveData<List<Todo>> todos = new MutableLiveData<>();
    private MutableLiveData<CountTodo> countTodoLiveData = new MutableLiveData<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CountTodo countTodo = new CountTodo();

    public TodoRepo() {
        countTodoLiveData.setValue(countTodo);
        db.collection("users").document(UserRepo.uid).collection("todos").addSnapshotListener((value, error) -> {
            todos.setValue(new ArrayList<>());
            int total = 0;
            for (QueryDocumentSnapshot doc : value) {
                List<Todo> currentTodos = todos.getValue();
                Todo todo = doc.toObject(Todo.class);
                todo.setId(doc.getId());
                currentTodos.add(todo);
                todos.setValue(currentTodos);
                total++;
            }
            countTodo.setTotalTodo(total);
            countTodoLiveData.setValue(countTodo);
        });

        db.collection("users").document(UserRepo.uid).collection("todos").whereEqualTo("completed", true).addSnapshotListener((value, error) -> {
            int total = 0;
            for (QueryDocumentSnapshot doc : value) {
                total++;
            }
            countTodo.setCompletedTodo(total);
            countTodoLiveData.setValue(countTodo);
        });
    }


    public LiveData<List<Todo>> getTodos() {
        return todos;
    }


    public void setTodos(MutableLiveData<List<Todo>> todos) {
        this.todos = todos;
    }

    public void checkTodo(String id) {
        List<Todo> todoList = todos.getValue();
        for (Todo todo : todoList) {
            if (todo.getId().equals(id)) {
                todo.setCompleted(!todo.getCompleted());
                db.collection("users").document(UserRepo.uid).collection("todos").document(id).update("completed", todo.getCompleted());
            }
        }
    }


    public Task<Void> addTodo(Todo todo) {
        DocumentReference doc = db.collection("users").document(UserRepo.uid).collection("todos").document();
        todo.setId(doc.getId());
        return doc.set(todo.toJson()).continueWithTask(task -> task);
    }

    public LiveData<CountTodo> getCountTodoLiveData() {
        return countTodoLiveData;
    }
}
