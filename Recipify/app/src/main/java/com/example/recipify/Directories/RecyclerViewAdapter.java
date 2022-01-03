package com.example.recipify.Directories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipify.R;
import com.example.recipify.RecipeViewer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {

    private Context mContext ;
    private List<Recipe> mData ;

    public RecyclerViewAdapter(Context mContext, List<Recipe> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.individual_recipe_card,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.recipe_title.setText(mData.get(position).getTitle());
        holder.recipe_servings.setText(Integer.toString(mData.get(position).getAmountOfDishes()) );
        holder.recipe_duration.setText( displayDuration(mData.get(position).getReadyInMins()) );
        holder.recipe_health_score.setText(mData.get(position).getHealthScore());

        Picasso.get().load(mData.get(position).getThumbnail()).into(holder.recipe_thumbnail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeViewer.class);
                intent.putExtra("id",mData.get(position).getId());
                intent.putExtra("titre",mData.get(position).getTitle());
                intent.putExtra("summary",mData.get(position).getSummary());
                intent.putExtra("step",mData.get(position).getStep());
                intent.putExtra("img",mData.get(position).getThumbnail());
                intent.putExtra("servings", mData.get(position).getAmountOfDishes());
                intent.putExtra("readyInMins", mData.get(position).getReadyInMins());
                intent.putExtra("healthScore",mData.get(position).getHealthScore());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        TextView recipe_title, recipe_servings, recipe_duration, recipe_health_score;
        ImageView recipe_thumbnail;
        CardView cardView ;

        public Holder(View itemView) {
            super(itemView);
            recipe_title = (TextView) itemView.findViewById(R.id.recipe_title) ;
            recipe_thumbnail = (ImageView) itemView.findViewById(R.id.recipe_thumbnail);
            recipe_servings = (TextView) itemView.findViewById(R.id.recipe_servings);
            recipe_duration = (TextView) itemView.findViewById(R.id.recipe_duration);
            recipe_health_score = (TextView) itemView.findViewById(R.id.recipe_health_score);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

    public String displayDuration (int duration){

        String display = "";

        if(duration == 60){

            display += "1h";

        } else if(duration > 60){

            display += duration / 60 + "h" + duration % 60 + "min";

        } else {

           display =+ duration + "min";
        }

        return  display;
    }
}
