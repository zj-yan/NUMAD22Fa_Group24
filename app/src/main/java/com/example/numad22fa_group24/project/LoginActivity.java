package com.example.numad22fa_group24.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.util.Utils;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput;
    EditText passwordInput;
    TextView signUp;
    Button loginButton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        emailInput = findViewById(R.id.signIn_email2);
        passwordInput = findViewById(R.id.signIn_password2);
        loginButton = findViewById(R.id.login_button2);
        signUp = findViewById(R.id.signup_link2);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getUid() != null) {
            Utils.signOut();
        }

        loginButton.setOnClickListener(view -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            login(email, password);
        });

        signUp.setOnClickListener(view -> {
            startActivity(new Intent(this, RegistrationActivity.class));
        });
    }

    private void login(String email, String password) {
        Utils.signOut();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,"Email or password can't be empty",
                    Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            emailInput.setError("Invalid Email");
            Toast.makeText(this, "Please enter valid email address",
                    Toast.LENGTH_SHORT).show();
        } else if(password.length()<6) {
            passwordInput.setError("Invalid Password");
            Toast.makeText(this, "Password must be at least 6 characters",
                    Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Utils.updateToken(firebaseAuth.getUid());
                    startActivity(new Intent(LoginActivity.this, WorryActivity.class));
                } else {
                    Toast.makeText(this, "Error in login", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}