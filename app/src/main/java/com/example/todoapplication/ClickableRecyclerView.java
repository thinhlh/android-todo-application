package com.example.todoapplication;

import java.util.List;

public interface ClickableRecyclerView<T> {
    void setItems(List<T> items);

    List<T> getItems();

    void setClickListener(RecyclerViewClickListener<T> listener);
}