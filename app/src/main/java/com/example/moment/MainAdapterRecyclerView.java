package com.example.moment;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapterRecyclerView extends RecyclerView.Adapter<MainAdapterRecyclerView.ViewHolder> {
    ArrayList<MainModelRecyclerView> mainModelRecyclerViews;
    Context context;

    public MainAdapterRecyclerView(Context context, ArrayList<MainModelRecyclerView> mainModelRecyclerViews){
        this.context = context;
        this.mainModelRecyclerViews = mainModelRecyclerViews;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(mainModelRecyclerViews.get(position).getLangLogo());
    }

    @Override
    public int getItemCount() {
        return mainModelRecyclerViews.size();
    }

    public class ViewHoler{

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
