package com.example.numad22fa_group24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.numad22fa_group24.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button weather = findViewById(R.id.weatherButton);
        weather.setOnClickListener(view -> weatherActivity());

        Button stickIt = findViewById(R.id.stickItButton);
        stickIt.setOnClickListener(view -> loginActivity());

        Button about = findViewById(R.id.about_btn);
        about.setOnClickListener(view -> aboutActivity());

        Button unworriedStore = findViewById(R.id.btn_unworried_store);
        unworriedStore.setOnClickListener(view -> projectActivity());
    }

    private void weatherActivity() {
        Intent intent = new Intent(this, Weather.class);
        startActivity(intent);
    }

    private void loginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void aboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void projectActivity() {
        Intent intent = new Intent(this, com.example.numad22fa_group24.project.LoginActivity.class);
        startActivity(intent);
    }
}