package com.example.recipify.Directories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Mdp extends AppCompatActivity {

    EditText mdp_email;
    EditText mdp_pass;
    EditText mdp_new_pass;
    Button mdp_confirmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdp);
        mdp_email=findViewById(R.id.field_email);
        mdp_pass=findViewById(R.id.field_password);
        mdp_new_pass=findViewById(R.id.mdp_new_pass);
        mdp_confirmer=findViewById(R.id.btn_login);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mdp_confirmer.setOnClickListener(p -> {
            assert user != null;
            mdp_email.setText(user.getEmail());
            user.updatePassword(mdp_new_pass.getText().toString());

        });



                    }
                }