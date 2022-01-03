package com.example.recipify.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class FragmentDescription extends Fragment {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_description, container, false);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        // Afficher les différentes informations sur la recette.
        // Afficher le titre.
        String titre = getActivity().getIntent().getExtras().getString("titre");
        TextView textTitre = (TextView) getView().findViewById(R.id.headerTextTitre);
        textTitre.setText(titre);

        // Afficher l'image.
        String thumbnail = getActivity().getIntent().getExtras().getString("img");
        ImageView img_thumbnail = (ImageView) getView().findViewById(R.id.image_recette);
        Picasso.get().load(thumbnail).into(img_thumbnail);

        // Afficher le nombre de portions.
        int servings = getActivity().getIntent().getExtras().getInt("servings");
        TextView textServings = (TextView) getView().findViewById(R.id.headerTextServings);
        textServings.setText("" + servings);

        // Afficher le temps de préparation.
        int readyInMins = getActivity().getIntent().getExtras().getInt("readyInMins");
        TextView textReadyInMins = (TextView) getView().findViewById(R.id.headerTextReadyInMins);
        textReadyInMins.setText("" + readyInMins);

        // Afficher si la recette est végan.
        Boolean isVegan = getActivity().getIntent().getExtras().getBoolean("isVegan");
        TextView textIsVegan = (TextView) getView().findViewById(R.id.headerTextVegan);
        String textToPrintVegan = (isVegan) ? "Oui" : "Non";
        textIsVegan.setText(textToPrintVegan);

         //Afficher si la recette est sans gluten.
        Boolean isGlutenFree = getActivity().getIntent().getExtras().getBoolean("isGlutenFree");
        TextView textIsGlutenFree = (TextView) getView().findViewById(R.id.headerTextGlutenFree);
        String textToPrintGlutenFree = (isGlutenFree) ? "Oui" : "Non";
        textIsGlutenFree.setText(textToPrintGlutenFree);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        Button button_pref;
        button_pref = getView().findViewById(R.id.button_pref);
        Button add_comment;
        add_comment = getView().findViewById(R.id.add_comment);
        String userid = auth.getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("listRecip");
        String id = getActivity().getIntent().getExtras().getString("id");




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exist = false;


                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    String value =  Objects.requireNonNull(postSnapshot.getValue()).toString();
                    if (value.equals(id)){
                        exist = true;
                        break;
                    }
                }

                if(!exist){
                    button_pref.setText("Add to favorites");
                   //

                }else{
                    button_pref.setText("Remove from favorites");
                    //myRef.child(id).rev();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Une erreure c'est produite.", Toast.LENGTH_SHORT).show();
            }
        });



        button_pref.setOnClickListener(v -> {

            if(button_pref.getText().toString().equals("Add to favorites")){
                myRef.child(id).setValue(id);
            }else {
                myRef.child(id).removeValue();
            }


        });

        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("Comments");

        EditText comment_field;
        comment_field = getView().findViewById(R.id.comment_field);
        add_comment.setOnClickListener(v -> {

           String commentUser = String.valueOf(comment_field.getText());
           myRef2.child(id).child(commentUser).setValue(commentUser);


        });


        Context test = this.getContext();

        final List<String> fruits_list = new ArrayList<String>();
        DatabaseReference myRef3 = FirebaseDatabase.getInstance().getReference().child("Comments").child(id);

        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exist = false;


                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    String comment =  Objects.requireNonNull(postSnapshot.getValue()).toString();
                    fruits_list.add(comment);
                }
                ListView lView = getView().findViewById(R.id.list_comments);
                // Initializing a new String Array

                // Create a List from String Array elements


                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                        (test, android.R.layout.simple_list_item_1, fruits_list);

                lView.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Une erreure c'est produite.", Toast.LENGTH_SHORT).show();
            }
        });



        DatabaseReference myRef5 = FirebaseDatabase.getInstance().getReference().child("Notes").child(id);

        TextView note_moy;
        note_moy = getView().findViewById(R.id.note_moy);


        EditText not_input;
        not_input = getView().findViewById(R.id.not_input);

        Button button2;
        button2 = getView().findViewById(R.id.button2);
        button2.setOnClickListener(v -> {

            String note = String.valueOf(not_input.getText());
            int noteInt = Integer.parseInt(note);
            myRef5.child(auth.getUid()).setValue(noteInt);


        });

        DatabaseReference myRef4 = FirebaseDatabase.getInstance().getReference().child("Notes").child(id);
        final List<Integer> notes_list_2 = new ArrayList<Integer>();

        myRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    String notes_list =  Objects.requireNonNull(postSnapshot.getValue()).toString();
                    int notes_list_int = Integer.parseInt(notes_list);
                    notes_list_2.add(notes_list_int);
                }
                int total =0;
                for(int note : notes_list_2){
                    total+=note;
                }


                if (notes_list_2.size() == 0){
                    note_moy.setText("7");

                }else{
                    total = total/notes_list_2.size();
                    note_moy.setText(String.valueOf(total));


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Une erreure c'est produite.", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
