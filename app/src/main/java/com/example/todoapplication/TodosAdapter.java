package com.example.todoapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.databinding.TodoListTileBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.TodoViewHolder> {

    private List<Todo> todos;
    //private TodoListTileBinding todoListTileBinding;

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        TodoListTileBinding todoListTileBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.todo_list_tile, parent, false);
        return new TodoViewHolder(todoListTileBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull TodoViewHolder holder, int position) {
        Todo currentTodo = todos.get(position);
        holder.todoListTileBinding.setTodo(currentTodo);
        holder.todoListTileBinding.completedCheckBox.setOnClickListener(v -> {
            TodoFragment.viewModel.checkTodo(currentTodo.getId());
        });
    }


    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return todos != null ? todos.size() : 0;
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        private final TodoListTileBinding todoListTileBinding;

        public TodoViewHolder(@NonNull TodoListTileBinding todoListTileBinding) {
            super(todoListTileBinding.getRoot());
            this.todoListTileBinding = todoListTileBinding;
        }

    }
}
