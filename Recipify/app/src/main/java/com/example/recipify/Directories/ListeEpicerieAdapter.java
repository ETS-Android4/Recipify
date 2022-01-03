package com.example.recipify.Directories;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipify.R;

import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;

public class ListeEpicerieAdapter extends ArrayAdapter {

    ArrayList<String> list;
    ArrayList<String> quantities;
    Context context;

    public ListeEpicerieAdapter(Context context, ArrayList<String> items, ArrayList<String> quantities) {
        super(context, R.layout.list_rows, items);
        this.context = context;
        this.list = items;
        this.quantities = quantities;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_rows, null);
            TextView nom = convertView.findViewById(R.id.nom_item);
            ImageView remove = convertView.findViewById(R.id.remove_item);
            TextView number = convertView.findViewById(R.id.numero_item);
            TextView quantity = convertView.findViewById(R.id.quantity_item);
            ImageView add_quantity = convertView.findViewById(R.id.add_quantity);
            ImageView remove_quantity = convertView.findViewById(R.id.remove_quantity);

            number.setText(position + 1 + ".");
            nom.setText(list.get(position));
            try {
                quantity.setText(""+quantities.get(position));
            }catch (IndexOutOfBoundsException e) {
                quantity.setText("0");
            }

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListeEpicerieActivity.removeItem(position);
                }
            });

            add_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int qty = Integer.parseInt(quantity.getText().toString());
                    quantity.setText("" + (qty + 1));
                    try{
                        ListeEpicerieActivity.quantities.remove(position);
                    } catch (IndexOutOfBoundsException e){

                    }
                    ListeEpicerieActivity.quantities.add(position, ""+(qty+1));
                }
            });

            remove_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int qty = Integer.parseInt(quantity.getText().toString());
                    if (qty != 0)
                        quantity.setText("" + (qty - 1));
                }
            });

        }
        return convertView;
    }

}
