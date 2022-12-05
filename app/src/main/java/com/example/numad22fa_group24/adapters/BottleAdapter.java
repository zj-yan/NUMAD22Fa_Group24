package com.example.numad22fa_group24.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.models.Bottle;

import java.util.List;

public class BottleAdapter extends RecyclerView.Adapter<BottleVH> {

    List<Bottle> list;

    public BottleAdapter(List<Bottle> list, Context context){
        this.list = list;
    }

    @NonNull
    @Override
    public BottleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottle, parent, false);
        BottleVH holder = new BottleVH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BottleVH holder, int position) {
        holder.bottleid.setText(list.get(position).getBottleID());
        holder.bottlecontent.setText(list.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class BottleVH extends RecyclerView.ViewHolder {
    TextView bottleid;
    TextView bottlecontent;
    ConstraintLayout bottleLayout;

    private BottleAdapter adapter;
    public BottleVH(@NonNull View itemView) {
        super(itemView);

        bottleLayout = itemView.findViewById(R.id.itemId);
        bottleid = itemView.findViewById(R.id.bottleid);
        bottlecontent = itemView.findViewById(R.id.bottlecontent);

    }
    public BottleVH bindAdapter(BottleAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}
