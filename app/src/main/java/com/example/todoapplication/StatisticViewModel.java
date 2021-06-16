package com.example.todoapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatisticViewModel extends ViewModel {

    private TodoRepo repo = new TodoRepo();

    private MutableLiveData<CountTodo> countTodoMutableLiveData = new MutableLiveData<>();

    public StatisticViewModel() {
        countTodoMutableLiveData.setValue(new CountTodo());
    }


    public LiveData<CountTodo> getCountTodoLiveData() {
        return repo.getCountTodoLiveData();
    }

    public void setCountTodoMutableLiveData(MutableLiveData<CountTodo> countTodoMutableLiveData) {
        this.countTodoMutableLiveData = countTodoMutableLiveData;
    }
}
