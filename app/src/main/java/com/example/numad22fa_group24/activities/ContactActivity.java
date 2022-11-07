package com.example.numad22fa_group24.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.adapters.UserAdapter;
import com.example.numad22fa_group24.models.User;
import com.example.numad22fa_group24.util.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContactActivity extends AppCompatActivity {

    ImageView logoutImg;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    List<User> userList;
    UserAdapter userAdapter;
    RecyclerView recyclerView;
    TextView usernameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        logoutImg = findViewById(R.id.img_logout);
        usernameView = findViewById(R.id.username_display);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userList = new ArrayList<>();

        logoutImg.setOnClickListener(view -> {
            logout();
        });

        DatabaseReference reference = firebaseDatabase.getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (!Objects.equals(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail(),
                            Objects.requireNonNull(user).getEmail())) {
                        userList.add(user);
                    } else {
                        usernameView.setText(user.getUsername());
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        recyclerView = findViewById(R.id.user_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(ContactActivity.this, userList);
        recyclerView.setAdapter(userAdapter);
    }

    private void logout() {
        Utils.signOut();
        startActivity(new Intent(ContactActivity.this, LoginActivity.class));
        ContactActivity.this.finish();
    }
}