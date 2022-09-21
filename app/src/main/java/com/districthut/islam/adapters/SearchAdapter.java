package com.districthut.islam.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.districthut.islam.Activities.search.SearchDetailActivity;
import com.districthut.islam.models.SearchModel;
import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.SampleSearchItemBinding;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    Context context;
    ArrayList<SearchModel> searchModels;

    public SearchAdapter(Context context, ArrayList<SearchModel> searchModels) {
        this.context = context;
        this.searchModels = searchModels;

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_search_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchModel question = searchModels.get(position);
        holder.binding.question.setText(question.getText());

        holder.binding.getRoot().setOnClickListener(c-> {
            Intent intent = new Intent(context, SearchDetailActivity.class);
            intent.putExtra("question_id", question.getId());
            intent.putExtra("title", question.getText());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return searchModels.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        SampleSearchItemBinding binding;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleSearchItemBinding.bind(itemView);
        }
    }
}
