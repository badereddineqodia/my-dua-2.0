package com.districthut.islam.adapters;

import static com.districthut.islam.utils.Helper.BASE_URL_UPLOADS;
import static com.districthut.islam.utils.Helper.CURRENT_ACTIVITY;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.districthut.islam.Activities.Hajj.HajjDetailActivity;
import com.districthut.islam.models.GenericDataModel;
import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.SampleLayoutOfHajjUmmrahItemsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HajjUmmrahAdapter extends RecyclerView.Adapter<HajjUmmrahAdapter.MyViewHolder> {

    Context context;
    ArrayList<GenericDataModel> items;

    public HajjUmmrahAdapter(Context context, ArrayList<GenericDataModel> items) {
        this.context = context;
        this.items = items;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_layout_of_hajj_ummrah_items, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        GenericDataModel model=items.get(position);
        holder.binding.gridTitle.setText(model.getTitle());
        holder.binding.gridSubtitle.setText(model.getCreated_at());
        Log.e("image", BASE_URL_UPLOADS + "/"+CURRENT_ACTIVITY+"/" + model.getImage());

        holder.itemView.setOnClickListener(c-> {
            Intent intent = new Intent(context, HajjDetailActivity.class);
            intent.putExtra("title", model.getTitle());
            intent.putExtra("detail", model.getDetail());
            intent.putExtra("image", model.getImage());
            intent.putExtra("created_at", model.getCreated_at());
            context.startActivity(intent);
        });

        Picasso.get()
                .load(BASE_URL_UPLOADS + "/"+CURRENT_ACTIVITY+"/" + model.getImage())
                .placeholder(R.drawable.imageplaceholder)
                .into(holder.binding.HajjUmrrahBackground);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       SampleLayoutOfHajjUmmrahItemsBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleLayoutOfHajjUmmrahItemsBinding.bind(itemView);
        }
    }
}
