package com.example.recipify.Directories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipify.Auth.Login;
import com.example.recipify.Auth.User;
import com.example.recipify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Profile extends AppCompatActivity {

    User user;
    Button signout_button;
    Button editprofile_button;
    Button profile_mdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        EditText nameT = findViewById(R.id.editText_name);
        TextView emailT = findViewById(R.id.editText_email);

        signout_button = findViewById(R.id.profil_deconnecter);
        editprofile_button = findViewById(R.id.profil_confirmer);
        profile_mdp = findViewById(R.id.profil_mdp);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference ref = User.USER_REF.child(auth.getCurrentUser().getUid());


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                nameT.setText(user.getFullname());
                emailT.setText(user.getEmail());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Profile.this, "Impossible de trouver vos informations.", Toast.LENGTH_SHORT).show();
            }
        });

        editprofile_button.setOnClickListener(x -> {
            String newName = String.valueOf(nameT.getText());

            if (!newName.equals(user.getFullname())) {
                if (newName.isEmpty())
                    Toast.makeText(Profile.this, "Veuillez entrer un nom.", Toast.LENGTH_SHORT).show();
                else {
                    User.updateName(auth.getCurrentUser().getUid(), newName);
                    Toast.makeText(Profile.this, "Profil sauvegardé.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signout_button.setOnClickListener(p -> {
            auth.signOut();
            Intent intent = new Intent(Profile.this, Login.class);
            startActivity(intent);
            finish();
        });

        profile_mdp.setOnClickListener(p -> {
            Intent intent = new Intent(Profile.this, Mdp.class);
            startActivity(intent);
            finish();
        });

        Button profile_delete;
        profile_delete = findViewById(R.id.profil_delete);
        profile_delete.setOnClickListener(p -> {

            DatabaseReference reff = User.USER_REF.child(auth.getCurrentUser().getUid());
            reff.getRef().removeValue();
            Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).delete();
            Intent intent = new Intent(Profile.this, FirstTime.class);
            startActivity(intent);
            finish();
        });
    }
}