package com.example.numad22fa_group24.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.adapters.MessageAdapter;
import com.example.numad22fa_group24.models.Sticker;
import com.example.numad22fa_group24.models.User;
import com.example.numad22fa_group24.notification.MyResponse;
import com.example.numad22fa_group24.notification.Token;
import com.example.numad22fa_group24.notification.APIService;
import com.example.numad22fa_group24.notification.Data;
import com.example.numad22fa_group24.notification.Sender;
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
    ImageView sticker1, sticker2, sticker3, sticker4, sticker5, sticker6, sticker7, sticker8, sticker9;

    String receiverUID, receiverName;
    String senderUID, senderName;
    String senderRoom, receiverRoom;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    RecyclerView recyclerView;
    MessageAdapter adapter;

    List<Sticker> stickers = new ArrayList<>();

    APIService apiService = Utils.getClient("https://fcm.googleapis.com/").create(APIService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

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
        recyclerView = findViewById(R.id.message_list);
        adapter = new MessageAdapter(ChatActivity.this, stickers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        DatabaseReference chatReference = firebaseDatabase.getReference().child("chats").child(senderRoom).child("messages");
        chatReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stickers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Sticker sticker = dataSnapshot.getValue(Sticker.class);
                    stickers.add(sticker);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        backArrow = findViewById(R.id.arrow_back);
        backArrow.setOnClickListener(view -> onBackPressed());

        sticker1 = findViewById(R.id.sticker1);
        sticker2 = findViewById(R.id.sticker2);
        sticker3 = findViewById(R.id.sticker3);
        sticker4 = findViewById(R.id.sticker4);
        sticker5 = findViewById(R.id.sticker5);
        sticker6 = findViewById(R.id.sticker6);
        sticker7 = findViewById(R.id.sticker7);
        sticker8 = findViewById(R.id.sticker8);
        sticker9 = findViewById(R.id.sticker9);
        sticker1.setOnClickListener(view -> sendSticker("sticker1"));
        sticker2.setOnClickListener(view -> sendSticker("sticker2"));
        sticker3.setOnClickListener(view -> sendSticker("sticker3"));
        sticker4.setOnClickListener(view -> sendSticker("sticker4"));
        sticker5.setOnClickListener(view -> sendSticker("sticker5"));
        sticker6.setOnClickListener(view -> sendSticker("sticker6"));
        sticker7.setOnClickListener(view -> sendSticker("sticker7"));
        sticker8.setOnClickListener(view -> sendSticker("sticker8"));
        sticker9.setOnClickListener(view -> sendSticker("sticker9"));
    }

    private void sendSticker(String imgName) {
        Date date = new Date();
        Sticker sticker = new Sticker(imgName, senderUID, date.getTime());
        DatabaseReference senderRef = firebaseDatabase.getReference().child("chats").child(senderRoom).child("messages");
        DatabaseReference receiverRef = firebaseDatabase.getReference().child("chats").child(receiverRoom).child("messages");
        senderRef.push().setValue(sticker).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                sendNotification(receiverUID, senderName, imgName);
                receiverRef.push().setValue(sticker);
            }
        });
    }

    private void sendNotification(String receiverUID, String senderName, String imgName) {
        DatabaseReference tokens = firebaseDatabase.getReference("tokens");
        Query query = tokens.orderByKey().equalTo(receiverUID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Token token = dataSnapshot.getValue(Token.class);
                    Data data = new Data(senderUID, receiverUID, "New Message", senderName + " sent you a sticker", imgName);
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