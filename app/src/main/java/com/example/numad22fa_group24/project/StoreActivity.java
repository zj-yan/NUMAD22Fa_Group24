package com.example.numad22fa_group24.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.models.Bottle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class StoreActivity extends AppCompatActivity {

    ConstraintLayout createBtn;
    ConstraintLayout storeBtn;
    ConstraintLayout friendsBtn;

    Button refreshBtn;
    TextView storeBottles;

    FirebaseAuth auth;
    FirebaseDatabase db;

    private static final int BOTTLE_NUM = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        // navbar - don't change
        createBtn = findViewById(R.id.btn_create);
        storeBtn = findViewById(R.id.btn_store);
        friendsBtn = findViewById(R.id.btn_friends);
        createBtn.setOnClickListener(view -> {
            startActivity(new Intent(StoreActivity.this, WorryActivity.class));
        });
        friendsBtn.setOnClickListener(view -> {
            startActivity(new Intent(StoreActivity.this, FriendActivity.class));
        });

        // test ui
        refreshBtn = findViewById(R.id.btn_refresh);
        storeBottles = findViewById(R.id.txt_store_bottles);

        // display bottles
        refreshBottles();

        // refresh bottles
        refreshBtn.setOnClickListener(view -> {
            refreshBottles();
        });
    }

    private void refreshBottles() {
        DatabaseReference reference = db.getReference().child("bottles");
        String currUser = auth.getUid();
        ArrayList<Bottle> bottles = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Random random = new Random();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Bottle bottle = dataSnapshot.getValue(Bottle.class);
                    if (!Objects.requireNonNull(bottle).getUserID().equals(currUser) && bottle.isPublic()) {
                        if (bottles.size() < BOTTLE_NUM) {
                            bottles.add(bottle);
                        } else {
                            boolean isTrue = random.nextBoolean();
                            int pos = random.nextInt(BOTTLE_NUM);
                            if (isTrue) {
                                bottles.remove(pos);
                                bottles.add(bottle);
                            }
                        }
                    }
                }
                // TODO: update bottles display
                storeBottles.setText(bottles.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}