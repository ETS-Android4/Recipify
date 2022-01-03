package com.example.recipify.Directories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipify.Auth.Login;
import com.example.recipify.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Cette classe contient les activités liées à la page d'accueil.
 */
public class Accueil extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // Les boutons
    Button sign_out_button;
    Button button_profile;
    Button button_search;
    Spinner spinner_filter;
    EditText search;
    JSONArray recipeArray;
    private List<Recipe> listRecipe = new ArrayList<>();
    private List<Recipe> listSearch;



    RecyclerView myrv;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    RecyclerViewAdapter adapter;


Context context = this;


    /**
     * Démarre le cycle de vie de l'activité de login.
     *
     * @param savedInstanceState, l'état
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        myrv = findViewById(R.id.recyclerview);
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        randomRecipes();

        search = findViewById(R.id.search);
        button_search = findViewById(R.id.button_search);
        button_search.setOnClickListener(v -> {
            String searchWord = String.valueOf(search.getText());
           searchRecipe(searchWord);
        });

        spinner_filter = (Spinner) findViewById(R.id.spinner_filter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filters, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_filter.setAdapter(adapter);

        spinner_filter.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.profil:
                intent = new Intent(Accueil.this, Profile.class);
                startActivity(intent);
                return true;
            case R.id.liste_epicerie:
                intent = new Intent(Accueil.this, ListeEpicerieActivity.class);
                startActivity(intent);
                return true;
            case R.id.signout:
                auth.signOut();
                intent = new Intent(Accueil.this, Login.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void randomRecipes() {
        String URL = "https://api.spoonacular.com/recipes/random?number=30&instructionsRequired=true&apiKey=bc01ac19ede345c1ac1e298729853545";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            recipeArray = (JSONArray) response.get("recipes");
                            for (int i = 0; i < recipeArray.length(); i++) {
                                JSONObject jsonObject = recipeArray.getJSONObject(i);
                                listRecipe.add(new Recipe(jsonObject.optString("id"),
                                        jsonObject.optString("title"),
                                        jsonObject.optString("image"),
                                        jsonObject.optString("summary"),
                                        jsonObject.optString("step"),
                                        Integer.parseInt(jsonObject.optString("servings")),
                                        Integer.parseInt(jsonObject.optString("readyInMinutes")),
                                        jsonObject.optString("healthScore"),
                                        jsonObject.optBoolean("vegan"),
                                        jsonObject.optBoolean("glutenFree")));
                            }
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(context, listRecipe);
                            myrv.setAdapter(myAdapter);
                            myrv.setItemAnimator(new DefaultItemAnimator());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myrv.setAlpha(0);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void searchRecipe(String search) {
        listSearch = new ArrayList<Recipe>();
        String URL="https://api.spoonacular.com/recipes/search?query=" + search + "&number=30&instructionsRequired=true&apiKey=bc01ac19ede345c1ac1e298729853545";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            recipeArray = (JSONArray) response.get("results");
                            for (int i = 0; i < recipeArray.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = recipeArray.getJSONObject(i);
                                listSearch.add(new Recipe(jsonObject1.optString("id"),
                                        jsonObject1.optString("title"),
                                        "https://spoonacular.com/recipeImages/" + jsonObject1.optString("image"),
                                        jsonObject1.optString("summary"),
                                        jsonObject1.optString("step"),
                                        Integer.parseInt(jsonObject1.optString("servings")),
                                        Integer.parseInt(jsonObject1.optString("readyInMinutes")),
                                        jsonObject1.optString("healthScore"),
                                        jsonObject1.optBoolean("vegan"),
                                        jsonObject1.optBoolean("glutenFree")));
                            }
                            if(listSearch.isEmpty()){
                                myrv.setAlpha(0);
                            }
                            else{
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(context, listSearch);
                                myrv.setAdapter(myAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void searchRecipeFilter(String search, String type) {
        listSearch = new ArrayList<Recipe>();
        String URL="https://api.spoonacular.com/recipes/search?" + type + "=" + search + "&number=30&instructionsRequired=true&apiKey=258e39e1329f44d69575bf1471197578";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            recipeArray = (JSONArray) response.get("results");
                            for (int i = 0; i < recipeArray.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = recipeArray.getJSONObject(i);
                                listSearch.add(new Recipe(jsonObject1.optString("id"),
                                        jsonObject1.optString("title"),
                                        "https://spoonacular.com/recipeImages/" + jsonObject1.optString("image"),
                                        jsonObject1.optString("summary"),
                                        jsonObject1.optString("step"),
                                        Integer.parseInt(jsonObject1.optString("servings")),
                                        Integer.parseInt(jsonObject1.optString("readyInMinutes")),
                                        jsonObject1.optString("healthScore"),
                                        jsonObject1.optBoolean("vegan"),
                                        jsonObject1.optBoolean("glutenFree")));;
                            }
                            if(listSearch.isEmpty()){
                                myrv.setAlpha(0);
                            }
                            else{
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(context, listSearch);
                                myrv.setAdapter(myAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);

        String f = parent.getItemAtPosition(position).toString();

        if(f == "Default"){
            randomRecipes();

        } else if (f == "Vegan"){
            searchRecipeFilter(f, "diet");

        } else if(f == "No gluten"){
            searchRecipeFilter("Gluten", "intolerances");

        } else{
            searchRecipeFilter(f, "cuisine");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        randomRecipes();
    }
}