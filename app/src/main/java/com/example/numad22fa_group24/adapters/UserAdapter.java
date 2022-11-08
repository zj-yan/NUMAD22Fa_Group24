package com.example.numad22fa_group24.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.activities.ChatActivity;
import com.example.numad22fa_group24.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context contactActivity;
    List<User> userList;

    public UserAdapter(Context contactActivity, List<User> userList) {
        this.contactActivity = contactActivity;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contactActivity).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.usernameView.setText(user.getUsername());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(contactActivity, ChatActivity.class);
            intent.putExtra("username", user.getUsername());
            intent.putExtra("userid", user.getUid());
            contactActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView usernameView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameView = itemView.findViewById(R.id.username);
        }
    }
}
