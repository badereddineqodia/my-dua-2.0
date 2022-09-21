package com.districthut.islam.adapters.Quran;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.SampleAyaListItemBinding;

public class AyaDialogListAdapter extends RecyclerView.Adapter<AyaDialogListAdapter.AyaDialogListViewHolder> {

    Context context;
    int aya;
    onAyaClicked listener;
    int currentVisibleAya;

    public AyaDialogListAdapter(Context context, int aya, int currentVisibleAya, onAyaClicked listener) {
        this.context = context;
        this.aya = aya;
        this.listener = listener;
        this.currentVisibleAya = currentVisibleAya;
    }

    @NonNull
    @Override
    public AyaDialogListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AyaDialogListViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_aya_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AyaDialogListViewHolder holder, int position) {
//        holder.binding.setAya(position);
//        if(position == currentVisibleAya)
//            holder.binding.aya.setBackgroundColor(context.getResources().getColor(R.color.green_accent));
//        else
//            holder.binding.aya.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        holder.binding.getRoot().setOnClickListener(c-> {
            listener.onClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return aya;
    }

    public class AyaDialogListViewHolder extends RecyclerView.ViewHolder {

        SampleAyaListItemBinding binding;

        public AyaDialogListViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleAyaListItemBinding.bind(itemView);
        }
    }

    public interface onAyaClicked {
        public void onClicked(int position);
    }
}
