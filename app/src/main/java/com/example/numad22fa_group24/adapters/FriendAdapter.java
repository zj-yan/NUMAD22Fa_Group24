package com.example.numad22fa_group24.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.project.ChatActivity;
import com.example.numad22fa_group24.models.User;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendVH>{

    List<User> list;
    Context friendActivity;
    public FriendAdapter(List<User> list, Context context){
        this.list = list;
        this.friendActivity = context;
    }

    @NonNull
    @Override
    public FriendVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        FriendVH holder = new FriendVH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendVH holder, int position) {
        User user = list.get(position);
        holder.friendname.setText(user.getUsername());
//        holder.friendstatus.setText(user.getStatus());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(friendActivity, ChatActivity.class);
            intent.putExtra("username", user.getUsername());
            intent.putExtra("userid", user.getUid());
            friendActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class FriendVH extends RecyclerView.ViewHolder {
    TextView friendstatus;
    TextView friendname;
    ConstraintLayout friendLayout;

    private FriendAdapter adapter;
    public FriendVH(@NonNull View itemView) {
        super(itemView);

        friendLayout = itemView.findViewById(R.id.oneLineFriendLayout);
        friendname = itemView.findViewById(R.id.friend_name);
//        friendstatus = itemView.findViewById(R.id.friend_status);
    }

    public FriendVH bindAdapter(FriendAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}
