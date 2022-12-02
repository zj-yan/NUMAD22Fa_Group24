package com.example.numad22fa_group24.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;

import com.example.numad22fa_group24.R;

public class FriendActivity extends AppCompatActivity {

    ConstraintLayout createBtn;
    ConstraintLayout storeBtn;
    ConstraintLayout friendsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        createBtn = findViewById(R.id.btn_create);
        storeBtn = findViewById(R.id.btn_store);
        friendsBtn = findViewById(R.id.btn_friends);

        storeBtn.setOnClickListener(view -> {
            startActivity(new Intent(FriendActivity.this, StoreActivity.class));
        });
        createBtn.setOnClickListener(view -> {
            startActivity(new Intent(FriendActivity.this, WorryActivity.class));
        });
    }
}