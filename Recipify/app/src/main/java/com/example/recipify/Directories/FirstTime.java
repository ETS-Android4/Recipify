package com.example.recipify.Directories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.recipify.Auth.Login;
import com.example.recipify.Auth.Signup;
import com.example.recipify.MainActivity;
import com.example.recipify.R;
import com.google.firebase.auth.FirebaseAuth;

public class FirstTime extends AppCompatActivity {
    Button sign_in_first;
    Button sign_up_first;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        sign_in_first = findViewById(R.id.signin_first);
        sign_up_first = findViewById(R.id.signup_first);

        sign_in_first.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        sign_up_first.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Signup.class);
            startActivity(intent);
            finish();
        });

    }
}