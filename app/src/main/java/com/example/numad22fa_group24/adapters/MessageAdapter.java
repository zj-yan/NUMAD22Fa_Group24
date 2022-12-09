package com.example.numad22fa_group24.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.models.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    Context context;
    List<Message> messages;
    FirebaseAuth auth;

    public MessageAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        auth = FirebaseAuth.getInstance();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateTime = sfd.format(new Date(message.getTimeStamp()));
        String [] splitString = dateTime.split(" ");
        String messageTime = splitString[1];

        if (message.getSenderID().equals(auth.getUid())) {
            holder.llReceived.setVisibility(View.GONE);
            holder.llSent.setVisibility(View.VISIBLE);

            holder.sentMessageView.setText(message.getContent());
            holder.sentTimeView.setText(messageTime);

        } else {
            holder.llSent.setVisibility(View.GONE);
            holder.llReceived.setVisibility(View.VISIBLE);

            holder.receivedMessageView.setText(message.getContent());
            holder.receivedTimeView.setText(messageTime);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView sentMessageView;
        TextView sentTimeView;
        TextView receivedMessageView;
        TextView receivedTimeView;
        LinearLayout llSent;
        LinearLayout llReceived;

        @SuppressLint("CutPasteId")
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            sentMessageView = itemView.findViewById(R.id.tvSentMessage);
            sentTimeView = itemView.findViewById(R.id.tvSentMessageTime);
            receivedMessageView = itemView.findViewById(R.id.tvReceivedMessage);
            receivedTimeView = itemView.findViewById(R.id.tvReceivedMessageTime);
            llSent = itemView.findViewById(R.id.llSent);
            llReceived = itemView.findViewById(R.id.llReceived);
        }
    }

}
