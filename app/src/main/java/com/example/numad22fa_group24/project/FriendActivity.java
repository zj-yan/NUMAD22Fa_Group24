package com.example.numad22fa_group24.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.adapters.BottleAdapter;
import com.example.numad22fa_group24.adapters.FriendAdapter;
import com.example.numad22fa_group24.models.Bottle;
import com.example.numad22fa_group24.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FriendActivity extends AppCompatActivity {

    ConstraintLayout createBtn;
    ConstraintLayout storeBtn;
    ConstraintLayout friendsBtn;

    TextView friendList;

    FirebaseAuth auth;
    FirebaseDatabase db;

    RecyclerView frienddisplay;
    FriendAdapter friendAdapter;
    ArrayList<User> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        friends = new ArrayList<>();
        frienddisplay = findViewById(R.id.frienddisplay);
        frienddisplay.setLayoutManager(new LinearLayoutManager(this));

        // navbar - don't change
        createBtn = findViewById(R.id.btn_create);
        storeBtn = findViewById(R.id.btn_store);
        friendsBtn = findViewById(R.id.btn_friends);
        storeBtn.setOnClickListener(view -> {
            startActivity(new Intent(FriendActivity.this, StoreActivity.class));
        });
        createBtn.setOnClickListener(view -> {
            startActivity(new Intent(FriendActivity.this, WorryActivity.class));
        });

        // test ui
        frienddisplay = findViewById(R.id.frienddisplay);
        getFriendList();
    }
    private void getFriendList() {
        DatabaseReference ref = db.getReference().child("friends").child(Objects.requireNonNull(auth.getUid()));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<User> friends = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String friendID = dataSnapshot.getKey();
                    DatabaseReference tempRef = db.getReference().child("users").child(Objects.requireNonNull(friendID));
                    tempRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            User user = snapshot2.getValue(User.class);
                            friends.add(user);

                            // TODO: display friend list
                            friendAdapter = new FriendAdapter(friends, FriendActivity.this);
                            frienddisplay.setAdapter(friendAdapter);
//                            friendList.setText(friends.toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    public void updateMyFriends() {
//        frienddisplay.setAdapter(null);
//        friends = new ArrayList<>();
//
//        DatabaseReference reference = db.getReference()
//                .child("user-bottles")
//                .child(Objects.requireNonNull(auth.getUid()));
//
//
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Bottle bottle = dataSnapshot.getValue(Bottle.class);
//                    bottles.add(bottle);
//                    // Log.i(TAG, String.valueOf(bottle.getContent()));
//
//                }
//
//                // TODO: update recycler view
//                bottleAdapter = new BottleAdapter(bottles, WorryActivity.this);
//                bottledisplay.setAdapter(bottleAdapter);
////                myBottles.setText(bottles.toString());
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
}