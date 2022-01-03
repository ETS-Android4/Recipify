package com.example.recipify;

import android.content.Intent;
import android.os.Bundle;

import com.example.recipify.Auth.*;
import com.example.recipify.Directories.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
//TODO Signup !
//TODO Login !
//TODO Reset le password
//TODO Changer les infos
//TODO Se deconecter !


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            // Si l'utilisateur est déjà connecté.
            if (auth.getCurrentUser() != null) {
                Intent intent = new Intent(getApplicationContext(), Accueil.class);
                startActivity(intent);
                finish();
            }
            // Si l'utilisateur n'est pas déjà connecté.
            else {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        }, 0);
    }
}