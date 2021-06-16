package com.example.todoapplication;

import com.example.todoapplication.ClickableRecyclerView;

public interface SwipeAbleRecyclerView<T> extends ClickableRecyclerView<T> {

    void remove(int position);
}