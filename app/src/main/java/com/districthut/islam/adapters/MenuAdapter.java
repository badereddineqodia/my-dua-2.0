package com.districthut.islam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.districthut.islam.models.MoreModel;
import com.mirfatif.noorulhuda.R;
import com.districthut.islam.utils.OnRecyclerViewItemClickListener;
import com.mirfatif.noorulhuda.databinding.ItemRowBinding;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MoreViewHolder> {

    Context context;
    ArrayList<MoreModel> moreModels;
    OnRecyclerViewItemClickListener listener;

    public MenuAdapter(Context context, ArrayList<MoreModel> moreModels) {
        this.context = context;
        this.moreModels = moreModels;
    }

    @NonNull
    @Override
    public MoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new MoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreViewHolder holder, final int position) {
        MoreModel model = moreModels.get(position);
        holder.binding.image.setImageResource(model.getImage());
        holder.binding.text.setText(model.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecyclerViewItemClicked(position, view.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return moreModels.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener)
    {
        this.listener = listener;
    }

    public class MoreViewHolder extends RecyclerView.ViewHolder {

        ItemRowBinding binding;

        public MoreViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemRowBinding.bind(itemView);
        }
    }

}
