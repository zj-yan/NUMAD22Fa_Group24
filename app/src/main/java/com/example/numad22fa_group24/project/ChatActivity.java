package com.example.numad22fa_group24.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.adapters.MessageAdapter;
import com.example.numad22fa_group24.models.Message;
import com.example.numad22fa_group24.models.User;
import com.example.numad22fa_group24.notification.APIService;
import com.example.numad22fa_group24.notification.Data;
import com.example.numad22fa_group24.notification.MyResponse;
import com.example.numad22fa_group24.notification.Sender;
import com.example.numad22fa_group24.notification.Token;
import com.example.numad22fa_group24.util.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    TextView receiverNameView;
    ImageView backArrow;
    ImageView sendArrow;
    EditText editText;

    String receiverUID, receiverName;
    String senderUID, senderName;
    String senderRoom, receiverRoom;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    RecyclerView recyclerView;
    MessageAdapter adapter;

    List<Message> messages = new ArrayList<>();

    APIService apiService = Utils.getClient("https://fcm.googleapis.com/").create(APIService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // set receiver and sender name
        receiverName = getIntent().getStringExtra("username");
        receiverNameView = findViewById(R.id.receiver_name);
        receiverNameView.setText(receiverName);
        DatabaseReference reference = firebaseDatabase.getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (Objects.equals(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail(),
                            Objects.requireNonNull(user).getEmail())) {
                        senderName = user.getUsername();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        // get receiver and sender uid
        receiverUID = getIntent().getStringExtra("userid");
        senderUID = firebaseAuth.getUid();

        // create chat rooms
        senderRoom = senderUID + receiverUID;
        receiverRoom = receiverUID + senderUID;

        // set up recycler view
        recyclerView = findViewById(R.id.message_list2);
        adapter = new MessageAdapter(ChatActivity.this, messages);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(messages.size() - 1);

        DatabaseReference chatReference = firebaseDatabase.getReference().child("chats").child(senderRoom).child("project-messages");
        chatReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    messages.add(message);
                }
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messages.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        backArrow = findViewById(R.id.arrow_back);
        backArrow.setOnClickListener(view -> onBackPressed());

        editText = findViewById(R.id.message_edit);

        sendArrow = findViewById(R.id.arrow_send);
        sendArrow.setOnClickListener(view -> {
            String content = editText.getText().toString();
            sendMessage(content);
            editText.setText("");
        });

    }

    private void sendMessage(String content) {
        if (content.equals("")) return;
        Date date = new Date();
        Message message = new Message(senderUID, receiverUID, content, date.getTime());
        DatabaseReference senderRef = firebaseDatabase.getReference().child("chats").child(senderRoom).child("project-messages");
        DatabaseReference receiverRef = firebaseDatabase.getReference().child("chats").child(receiverRoom).child("project-messages");
        senderRef.push().setValue(message).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                sendNotification(receiverUID, senderName, content);
                receiverRef.push().setValue(message);
            }
        });
    }

    private void sendNotification(String receiverUID, String senderName, String content) {
        DatabaseReference tokens = firebaseDatabase.getReference("tokens");
        Query query = tokens.orderByKey().equalTo(receiverUID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Token token = dataSnapshot.getValue(Token.class);
                    Data data = new Data(senderUID, receiverUID, senderName + " sent you a message", content, "sticker1");
                    Sender sender = new Sender(data, Objects.requireNonNull(token).getToken());
                    apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<MyResponse> call, @NonNull Response<MyResponse> response) {
                            if (response.isSuccessful()) {
                                System.out.println("success");
                            } else {
                                System.out.println("failed");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MyResponse> call, @NonNull Throwable t) {
                            System.out.println("failed");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}