package com.example.numad22fa_group24.util;

import android.content.Context;
import android.widget.Toast;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.notification.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.lang.reflect.Field;
import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String url) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }


    public static int getImgId(String imgName) {
        int resId = -1;
        try {
            Field idField = R.drawable.class.getDeclaredField(imgName);
            resId = idField.getInt(idField);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return resId;
    }

    // update token when login
    public static void updateToken(String userid) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult();
                updateToken(userid, token);
            }
        });
    }

    public static void updateToken(String userid, String token) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tokens");
        reference.child(userid).setValue(new Token(token));
    }

    public static void updateDeviceToken(final Context context, String token)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser= firebaseAuth.getCurrentUser();

        if(currentUser!=null) {

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference databaseReference = rootRef.child("tokens").child(currentUser.getUid());
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("tokens", token);

            databaseReference.setValue(hashMap).addOnCompleteListener(task -> {
                if(!task.isSuccessful()) {
                    Toast.makeText(context, "failed_to_save_device_token", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void signOut() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getUid() != null) {
            Utils.updateToken(firebaseAuth.getUid(), "");
            firebaseAuth.signOut();
        }
    }
}
