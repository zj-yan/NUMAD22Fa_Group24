package com.example.numad22fa_group24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.numad22fa_group24.R;
import com.example.numad22fa_group24.models.User;
import com.example.numad22fa_group24.util.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    EditText usernameInput, emailInput, passwordInput;
    Button signUpButton;
    TextView signIn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameInput = findViewById(R.id.signUp_name);
        emailInput = findViewById(R.id.signUp_email);
        passwordInput = findViewById(R.id.signUp_password);
        signUpButton = findViewById(R.id.signUp_btn);
        signIn = findViewById(R.id.signIn_link);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        signIn.setOnClickListener(view -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        });

        signUpButton.setOnClickListener(view -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            String username = usernameInput.getText().toString();

            signUp(username, email, password);

        });
    }

    private void signUp(String username, String email, String password) {

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegistrationActivity.this,"Empty line not allowed",
                    Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            emailInput.setError("Invalid Email");
            Toast.makeText(RegistrationActivity.this, "Please enter valid email address",
                    Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6){
            passwordInput.setError("Invalid Password");
            Toast.makeText(RegistrationActivity.this,"Password must be at least 6 characters",
                    Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DatabaseReference reference = firebaseDatabase.getReference().child("users")
                            .child(Objects.requireNonNull(firebaseAuth.getUid()));
                    User user = new User(firebaseAuth.getUid(), username, email, "Online");
                    reference.setValue(user).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            progressDialog.dismiss();
                            Utils.updateToken(firebaseAuth.getUid());
                            startActivity(new Intent(RegistrationActivity.this, ContactActivity.class));
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, "Error in creating the user"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this,"Error in registration"
                            ,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}