package com.example.numad22fa_group24.project;

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
        setContentView(R.layout.activity_registration2);

        usernameInput = findViewById(R.id.signUp_name2);
        emailInput = findViewById(R.id.signUp_email2);
        passwordInput = findViewById(R.id.signUp_password2);
        signUpButton = findViewById(R.id.signUp_btn2);
        signIn = findViewById(R.id.signIn_link2);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        signIn.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
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
            Toast.makeText(this,"Empty line not allowed",
                    Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            emailInput.setError("Invalid Email");
            Toast.makeText(this, "Please enter valid email address",
                    Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6){
            passwordInput.setError("Invalid Password");
            Toast.makeText(this,"Password must be at least 6 characters",
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
                            startActivity(new Intent(this, WorryActivity.class));
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(this, "Error in creating the user"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(this,"Error in registration"
                            ,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}