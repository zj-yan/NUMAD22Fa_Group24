package com.example.numad22fa_group24.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.models.Sticker;
import com.example.numad22fa_group24.util.Utils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    List<Sticker> stickers;

    int SENDER_VIEW = 1;
    int RECEIVER_VIEW = 2;

    public MessageAdapter(Context context, List<Sticker> stickers) {
        this.context = context;
        this.stickers = stickers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER_VIEW) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_sender, parent, false);
            return new senderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_receiver, parent, false);
            return new receiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Sticker sticker = stickers.get(position);
        if (holder.getClass() == senderViewHolder.class) {
            senderViewHolder viewHolder = (senderViewHolder) holder;
            viewHolder.imageView.setImageResource(Utils.getImgId(sticker.getImageName()));
        } else {
            receiverViewHolder viewHolder = (receiverViewHolder) holder;
            viewHolder.imageView.setImageResource(Utils.getImgId(sticker.getImageName()));
        }
    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }

    @Override
    public int getItemViewType(int position) {
        Sticker sticker = stickers.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(sticker.getSenderId())) {
            return SENDER_VIEW;
        } else {
            return RECEIVER_VIEW;
        }
    }

    static class senderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sender_img);
        }
    }

    static class receiverViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public receiverViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.receiver_img);
        }
    }

}
