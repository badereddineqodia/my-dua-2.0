package com.districthut.islam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.districthut.islam.models.HadithsModel;
import com.mirfatif.noorulhuda.R;

import java.util.ArrayList;

public class HadithsAdapter extends RecyclerView.Adapter<HadithsAdapter.MyViewHolder>{

    Context context;
    ArrayList<HadithsModel> items;

    public HadithsAdapter(Context context, ArrayList<HadithsModel> items) {
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

        HadithsModel model=items.get(position);
        holder.name.setText(model.getName());
        holder.alphabet.setText(model.getAlphabet());
        holder.bookName.setText("From"+" "+model.getBookName()+",");
        holder.totalHadiths.setText(" "+model.getTotalHadiths()+"");

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,alphabet,bookName,totalHadiths;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.hadithsType);
            alphabet=itemView.findViewById(R.id.hadithWord);
            bookName=itemView.findViewById(R.id.hadithsBookName);
            totalHadiths=itemView.findViewById(R.id.totalNoOfHadiths);
        }
    }
}
