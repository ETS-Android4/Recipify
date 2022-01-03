package com.example.recipify.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipify.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class FragmentIngredients extends Fragment {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Afficher la liste des ingrédients.
        // Afficher le titre.
        String titre = getActivity().getIntent().getExtras().getString("titre");
        TextView textTitre = (TextView) getView().findViewById(R.id.headerTextTitre);
        textTitre.setText(titre);

        // Afficher l'image.
        String thumbnail = getActivity().getIntent().getExtras().getString("img");
        ImageView img_thumbnail = (ImageView) getView().findViewById(R.id.image_recette);
        Picasso.get().load(thumbnail).into(img_thumbnail);

        //String step = getActivity().getIntent().getExtras().getString("step");
        //TextView testStep = (TextView) getView().findViewById(R.id.headerTextStep);
        //testStep.setText(step);

        String summary = getActivity().getIntent().getExtras().getString("summary");
        TextView textSum = (TextView) getView().findViewById(R.id.headerTextSum);
        summary = summary.replaceAll("<(.*?)\\>"," ");
        textSum.setText(summary);

    }
}
