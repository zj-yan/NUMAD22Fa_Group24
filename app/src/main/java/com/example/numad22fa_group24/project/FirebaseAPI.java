package com.example.numad22fa_group24.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

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

public class FirebaseAPI {

    public static void createBottle(Bottle bottle) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        // add new bottle to user-bottles table
        DatabaseReference ref1 = db.getReference()
                .child("user-bottles")
                .child(Objects.requireNonNull(auth.getUid()));

        ref1.push().setValue(bottle);

        // add new bottle to bottles table
        DatabaseReference ref2 = db.getReference().child("bottles");
        ref2.push().setValue(bottle);
    }

    public static void deleteBottle(Bottle bottle) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference()
                .child("user-bottles")
                .child(Objects.requireNonNull(auth.getUid()));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Bottle curr = dataSnapshot.getValue(Bottle.class);
                    if (Objects.equals(curr, bottle)) {
                        dataSnapshot.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static ArrayList<Bottle> getMyBottles() {
        ArrayList<Bottle> bottles = new ArrayList<>();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference()
                .child("user-bottles")
                .child(Objects.requireNonNull(auth.getUid()));

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Bottle bottle = dataSnapshot.getValue(Bottle.class);
                    bottles.add(bottle);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return bottles;
    }

//    public static ArrayList<Bottle> getRandomBottles(int number) {
//        ArrayList<Bottle> bottles = new ArrayList<>();
//
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference reference = db.getReference().child("bottles");
//        String currUser = auth.getUid();
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int count = 0;
//                Random random = new Random();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Bottle bottle = dataSnapshot.getValue(Bottle.class);
//                    if (!bottle.getUserID().equals(currUser)) {
//                        if (count < number) {
//                            bottles.add(bottle);
//                            count += 1;
//                        } else {
//                            boolean isTrue = random.nextBoolean();
//                            int pos = random.nextInt(number);
//                            if (isTrue) {
//                                bottles.remove(pos);
//                                bottles.add(bottle);
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//
//        return bottles;
//    }
}
