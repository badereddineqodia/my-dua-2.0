package com.districthut.islam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.districthut.islam.models.HadithsTypeModel;
import com.mirfatif.noorulhuda.R;

import java.util.ArrayList;

public class HadithsTypeAdapter extends RecyclerView.Adapter<HadithsTypeAdapter.MyViewHolder>{

    Context context;
    ArrayList<HadithsTypeModel> items;
    public HadithsTypeAdapter(Context context, ArrayList<HadithsTypeModel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_layout_of_hadiths_type_items, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HadithsTypeModel model=items.get(position);
        holder.chapterNo.setText("Chapter "+model.getChapterNo());
        holder.rowCounter.setText(model.getRowCounter()+".");
        holder.type.setText(model.getType());
        holder.totalChapter.setText(model.getTotalChapter());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rowCounter,chapterNo,type,totalChapter;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rowCounter=itemView.findViewById(R.id.rowCounter);
            chapterNo=itemView.findViewById(R.id.chapterNo);
            type=itemView.findViewById(R.id.type);
            totalChapter=itemView.findViewById(R.id.totalNoOfChapters);
        }
    }
}
