package com.example.todoapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.databinding.FragmentTodosBinding;

import org.jetbrains.annotations.NotNull;

public class TodoFragment extends Fragment {

    public static TodoViewModel viewModel;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        FragmentTodosBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todos, container, false);
        viewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        View view = binding.getRoot();

        RecyclerView recyclerView = binding.todosRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        TodosAdapter adapter = new TodosAdapter();
        recyclerView.setAdapter(adapter);
        getTodos(adapter);

        return view;
    }

    private void getTodos(TodosAdapter adapter) {
        viewModel.getTodos().observe(getViewLifecycleOwner(), adapter::setTodos);
    }
}
