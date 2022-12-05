package com.example.numad22fa_group24.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendAdapter extends RecyclerView.Adapter<FriendVH>{
    @NonNull
    @Override
    public FriendVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class FriendVH extends RecyclerView.ViewHolder {

    public FriendVH(@NonNull View itemView) {
        super(itemView);
    }
}
