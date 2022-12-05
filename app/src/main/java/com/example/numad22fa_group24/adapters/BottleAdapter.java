package com.example.numad22fa_group24.adapters;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BottleAdapter extends RecyclerView.Adapter<BottleVH> {
    String content;

    @NonNull
    @Override
    public BottleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BottleVH holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 0;
    }
}

class BottleVH extends RecyclerView.ViewHolder {
    public BottleVH(@NonNull View itemView) {
        super(itemView);
    }
}
