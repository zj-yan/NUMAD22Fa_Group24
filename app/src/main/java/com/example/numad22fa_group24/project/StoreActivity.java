package com.example.numad22fa_group24.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.adapters.StoreAdapter;
import com.example.numad22fa_group24.models.Bottle;
import com.example.numad22fa_group24.util.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class StoreActivity extends AppCompatActivity {

    ConstraintLayout createBtn;
    ConstraintLayout storeBtn;
    ConstraintLayout friendsBtn;
    RecyclerView storeDisplay;
    StoreAdapter storeAdapter;
    ArrayList<Bottle> bottles = new ArrayList<>();

    FloatingActionButton refreshBtn;
    TextView storeBottles;

    FirebaseAuth auth;
    FirebaseDatabase db;

    private static final int BOTTLE_NUM = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        storeDisplay = findViewById(R.id.storedisplay);
        storeDisplay.setLayoutManager(new LinearLayoutManager(this));
        storeDisplay.addOnItemTouchListener(new RecyclerItemClickListener(this, storeDisplay,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Bottle current = bottles.get(position);
                        String userId = bottles.get(position).getUserID();
                        //connect(userId);
                        Dialog dialog = new Dialog(StoreActivity.this);
                        dialog.setContentView(R.layout.item_store);


                        TextView friendContent = dialog.findViewById(R.id.friendContent);
                        Button connectButton = dialog.findViewById(R.id.connect);
                        Log.i("Bottle", friendContent.toString());

                       // friendContent.setText("1");
//                        friendId.setText(current.getUserID());
                        friendContent.setText(current.getContent());

                        connectButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                connect(userId);
                                Snackbar snackbar = Snackbar.make
                                        (storeDisplay, "Connect with friend!",
                                                Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();


                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));


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
        //storeBottles = findViewById(R.id.txt_store_bottles);

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
        //ArrayList<Bottle> bottles = new ArrayList<>();

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
               // storeBottles.setText(bottles.toString());
                storeAdapter = new StoreAdapter(bottles, StoreActivity.this);
                storeDisplay.setAdapter(storeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void connect(String userID) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("placeholder", "placeholder");
        String myID = auth.getUid();

        DatabaseReference ref = db.getReference()
                .child("friends")
                .child(Objects.requireNonNull(myID));
        ref.child(userID).setValue(hashMap);

        DatabaseReference ref2 = db.getReference()
                .child("friends")
                .child(userID);
        ref2.child(myID).setValue(hashMap);
    }


}