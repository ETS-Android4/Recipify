package com.example.recipify.Directories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipify.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class ListeEpicerieActivity extends AppCompatActivity {

    static ListView listView;
    static ArrayList<String> items;
    static ArrayList<String> quantities;
    static ListeEpicerieAdapter adapter;
    static Context context;

    EditText input;
    ImageView enter;
    TextView quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_epicerie);

        listView = findViewById(R.id.listview);
        input = findViewById(R.id.input_item);
        enter = findViewById(R.id.add_item);

        items = new ArrayList<>();
        quantities = new ArrayList<>();

        adapter = new ListeEpicerieAdapter(this, items, quantities);
        listView.setAdapter(adapter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                if (text == null || text.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Entrer un item à ajouter.", Toast.LENGTH_SHORT).show();
                } else {
                    addItem(text);
                    input.setText("");
                    Toast.makeText(getApplicationContext(), "Item ajouté: " + text, Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadContent();
    }

    public void loadContent() {
        // Faire un appel a Firebase pour obtenir les données.
        File file = getApplicationContext().getFilesDir();
        File liste = new File(file, "liste.txt");
        File quantites = new File(file, "quantites.txt");
        byte[] content = new byte[(int) liste.length()]; // Contenu a obtenir;
        byte[] content_qty = new byte[(int) quantites.length()];

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(liste);
            fis.read(content);

            String s = new String(content);
            s = s.substring(1, s.length() - 1);
            String split[] = s.split(", ");


            if (split.length == 1 && split[0].isEmpty()) {
                items = new ArrayList<>();
            } else {
                items = new ArrayList<>(Arrays.asList(split));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            fis = new FileInputStream(quantites);
            fis.read(content_qty);

            String s = new String(content_qty);
            s = s.substring(1, s.length() - 1);
            String split[] = s.split(", ");

            if (split.length == 1 && split[0].isEmpty()) {
                quantities = new ArrayList<>();
            } else {
                quantities = new ArrayList<>(Arrays.asList(split));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        adapter = new ListeEpicerieAdapter(this, items, quantities);
        listView.setAdapter(adapter);
    }

    /*public void loadContent() throws ClassNotFoundException, IOException {
        File file = getApplicationContext().getFilesDir();
        File liste = new File(file, "liste.txt");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(liste))) {
           items = (HashMap<String, Integer>) ois.readObject();
        }

    }*/

    public void saveContent() {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream fos = new FileOutputStream(new File(path, "liste.txt"));
            fos.write(items.toString().getBytes());
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        try{
            FileOutputStream fos = new FileOutputStream(new File(path, "quantites.txt"));
            fos.write(quantities.toString().getBytes());
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addItem(String item) {
        items.add(item);
        quantities.add("1");
        saveContent();
        loadContent();
        //listView.setAdapter(adapter);
    }

    public static void removeItem(int indice) {
        items.remove(adapter.getItem(indice));
        quantities.remove(indice);
        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        saveContent();
        super.onDestroy();
    }
}