package com.districthut.islam.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.districthut.islam.models.GenericDataModel;
import com.mirfatif.noorulhuda.R;

import java.util.ArrayList;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.MyViewHolder> {

    Context context;
    ArrayList<GenericDataModel> stories;

    public StoriesAdapter(Context context, ArrayList<GenericDataModel> stories) {
        this.context = context;
        this.stories = stories;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_layout_of_stories_items, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GenericDataModel story= stories.get(position);

    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       public TextView title;
       public MyViewHolder(@NonNull View itemView) {
           super(itemView);
           title=itemView.findViewById(R.id.storiesTitle);
       }
   }

}