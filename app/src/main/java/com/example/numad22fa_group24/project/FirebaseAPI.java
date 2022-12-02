package com.example.numad22fa_group24.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.numad22fa_group24.activities.ContactActivity;
import com.example.numad22fa_group24.activities.RegistrationActivity;
import com.example.numad22fa_group24.models.Bottle;
import com.example.numad22fa_group24.models.User;
import com.example.numad22fa_group24.util.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FirebaseAPI {


    public static void createBottle(Bottle bottle, Context context) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference()
                .child("bottles")
                .child(Objects.requireNonNull(auth.getUid()));

        reference.push().setValue(bottle).addOnCompleteListener(task1 -> {
            if (!task1.isSuccessful()) {
                Toast.makeText(context, "Error in creating new bottle", Toast.LENGTH_SHORT).show();
            } else {
                // add new bottle to the bottle lists
            }
        });
    }

    public static ArrayList<Bottle> readBottles() {
        ArrayList<Bottle> bottles = new ArrayList<>();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference()
                .child("bottles")
                .child(Objects.requireNonNull(auth.getUid()));

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Bottle bottle = dataSnapshot.getValue(Bottle.class);
                    bottles.add(bottle);
                }
                // notify bottle adapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return bottles;
    }
}
