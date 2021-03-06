package com.movilbox.movilboxprueba.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private PostsListAdapter mAdapter;

    public  SwipeToDeleteCallback(PostsListAdapter adapter) {
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.deleteItem(position);
    }




}
