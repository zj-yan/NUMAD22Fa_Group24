package com.example.numad22fa_group24.adapters;

import android.app.Dialog;
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

public class StoreAdapter extends RecyclerView.Adapter<StoreVH>{
    List<Bottle> list;

    public StoreAdapter(List<Bottle> list, Context context){
        this.list = list;
    }

    @NonNull
    @Override
    public StoreVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottle, parent, false);

        StoreVH holder = new StoreVH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreVH holder, int position) {
        holder.bottlecontent.setText(list.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


class StoreVH extends RecyclerView.ViewHolder {
    TextView bottleid;
    TextView bottlecontent;
    ConstraintLayout bottleLayout;
    ConstraintLayout storeLayout;

    private StoreAdapter adapter;
    public StoreVH(@NonNull View itemView) {
        super(itemView);

        bottleLayout = itemView.findViewById(R.id.itemId);
        bottlecontent = itemView.findViewById(R.id.bottlecontent);
        storeLayout = itemView.findViewById(R.id.storeItemLayout);
    }

    public StoreVH bindAdapter(StoreAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}