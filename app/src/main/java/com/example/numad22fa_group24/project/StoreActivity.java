package com.example.numad22fa_group24.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;

import com.example.numad22fa_group24.R;

public class StoreActivity extends AppCompatActivity {

    ConstraintLayout createBtn;
    ConstraintLayout storeBtn;
    ConstraintLayout friendsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        createBtn = findViewById(R.id.btn_create);
        storeBtn = findViewById(R.id.btn_store);
        friendsBtn = findViewById(R.id.btn_friends);

        createBtn.setOnClickListener(view -> {
            startActivity(new Intent(StoreActivity.this, WorryActivity.class));
        });
        friendsBtn.setOnClickListener(view -> {
            startActivity(new Intent(StoreActivity.this, FriendActivity.class));
        });

    }
}