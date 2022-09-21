package com.districthut.islam.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.districthut.islam.models.BackgroundModel;
import com.mirfatif.noorulhuda.R;

import java.util.ArrayList;


public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.MyViewHolder>{

    Context context;
    ArrayList<BackgroundModel> backgroundList;

    public BackgroundAdapter(Context context, ArrayList<BackgroundModel> backgroundList) {
        this.context = context;
        this.backgroundList = backgroundList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_layout_of_backgrounds, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BackgroundModel model=backgroundList.get(position);
        holder.image.setImageResource(model.getImage());

    }

    @Override
    public int getItemCount() {
        return backgroundList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.backgroundImage);
        }
    }
}
