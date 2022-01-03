package com.example.recipify.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipify.Directories.Accueil;
import com.example.recipify.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Cette classe contient les activitesliees a l'authentification de l'utilisateur.
 */
public class Login extends AppCompatActivity {
    EditText login_email;
    EditText login_mdp;
    Button login_button;
    TextView login_text;

    /**
     * Demarre la logique du processus d'enregistrement d'un utilisateur dans la base de donnees.
     *
     * @param savedInstanceState L'état de l'application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_email = findViewById(R.id.field_email);
        login_mdp = findViewById(R.id.field_password);
        login_button = findViewById(R.id.btn_login);
        login_text = findViewById(R.id.login_text);

        login_text.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Signup.class);
            startActivity(intent);
            finish();
        });

        login_button.setOnClickListener(v -> registerUser());
    }

    /**
     * Enregistre un utilisateur dans la base de donnees Firebase.
     */
    private void registerUser() {
        String email, password;
        email = String.valueOf(login_email.getText());
        password = String.valueOf(login_mdp.getText());
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (email.isEmpty())
            Toast.makeText(Login.this, "Veuillez entrer votre courriel.", Toast.LENGTH_SHORT).show();
        else if (password.isEmpty())
            Toast.makeText(Login.this, "Veuillez entrer votre mot de passe.", Toast.LENGTH_SHORT).show();
        else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Login.this, Accueil.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Information de connexion invalide.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}