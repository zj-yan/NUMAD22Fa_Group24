package com.example.numad22fa_group24.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.adapters.BottleAdapter;
import com.example.numad22fa_group24.models.Bottle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class WorryActivity extends AppCompatActivity {

    ConstraintLayout createBtn;
    ConstraintLayout storeBtn;
    ConstraintLayout friendsBtn;
    RecyclerView bottledisplay;
    BottleAdapter bottleAdapter;
//    ArrayList<Bottle> bottles;

    TextView myBottles;
    Button saveBtn;
    Button deleteBtn;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch publicSwitch;
    TextInputEditText editText;

    FirebaseAuth auth;
    FirebaseDatabase db;

    private static final String TAG = "db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worry);

        // firebase authentication and database -- don't need to change
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

       // bottles = new ArrayList<>();
        // navbar -- don't need to change
        bottledisplay = findViewById(R.id.bottledisplay);
        bottledisplay.setLayoutManager(new LinearLayoutManager(this));
        createBtn = findViewById(R.id.btn_create);

        storeBtn = findViewById(R.id.btn_store);
        friendsBtn = findViewById(R.id.btn_friends);
        storeBtn.setOnClickListener(view -> {
            startActivity(new Intent(WorryActivity.this, StoreActivity.class));
        });
        friendsBtn.setOnClickListener(view -> {
            startActivity(new Intent(WorryActivity.this, FriendActivity.class));
        });

        // TODO: add recycler view for bottles, code below can be changed

        // ui for test -- can be deleted
        //myBottles = findViewById(R.id.txt_my_bottles);


        editText = findViewById(R.id.input_worry);
        saveBtn = findViewById(R.id.btn_save);
        deleteBtn = findViewById(R.id.btn_delete);
        publicSwitch = findViewById(R.id.switch_is_public);

        // update bottles when activity is initialized
        updateMyBottles();


        // get switch status
        final boolean[] isPublic = {false};
        publicSwitch.setOnCheckedChangeListener((compoundButton, b) -> isPublic[0] = b);

        // save new bottle
        saveBtn.setOnClickListener(view -> {
            String content = Objects.requireNonNull(editText.getText()).toString();
            Bottle newBottle = new Bottle(auth.getUid(), content, isPublic[0]);
            createBottle(newBottle);
        });

        deleteBtn.setOnClickListener(view -> {
            String bottleID = Objects.requireNonNull(editText.getText()).toString();
            deleteBottle(bottleID);
        });

    }

    // create new bottle in firebase database and update bottle list
    public void createBottle(Bottle bottle) {
        // add new bottle to user-bottles table
        DatabaseReference ref1 = db.getReference()
                .child("user-bottles")
                .child(Objects.requireNonNull(auth.getUid()));
        String key = ref1.push().getKey();
        bottle.setBottleID(key);
        ref1.child(Objects.requireNonNull(key)).setValue(bottle).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(WorryActivity.this, "failed to save new bottle", Toast.LENGTH_SHORT).show();
            } else {
                updateMyBottles();
            }
        });

        // add new bottle to bottles table
        DatabaseReference ref2 = db.getReference().child("bottles");
        ref2.child(key).setValue(bottle);
    }

    // delete bottle in firebase database and update bottle list
    public void deleteBottle(String bottleID) {
        if (bottleID.equals("")) return;
        DatabaseReference ref = db.getReference()
                .child("user-bottles")
                .child(Objects.requireNonNull(auth.getUid()))
                .child(bottleID);
        ref.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updateMyBottles();
            }
        });

        DatabaseReference ref2 = db.getReference().child("bottles").child(bottleID);
        ref2.removeValue();
    }

    // get my bottles from firebase database and update recycler view
    public void updateMyBottles() {
        ArrayList<Bottle> bottles = new ArrayList<>();
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
                    Log.i(TAG, String.valueOf(bottle.getContent()));

                }

                // TODO: update recycler view
                bottleAdapter = new BottleAdapter(bottles, WorryActivity.this);
                bottledisplay.setAdapter(bottleAdapter);
//                myBottles.setText(bottles.toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}