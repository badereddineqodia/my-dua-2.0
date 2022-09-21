package com.districthut.islam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.districthut.islam.Activities.DuaActivity;
import com.districthut.islam.Fragments.DuaDetailFragment;
import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.TopiclayoutBinding;

import java.util.ArrayList;

public class DuaTopicAdapter extends RecyclerView.Adapter<DuaTopicAdapter.DuaTopicViewHolder> {

    Context context;
    ArrayList<String> topics;
    String language;

    public DuaTopicAdapter(Context context, ArrayList<String> topics, String language) {
        this.context = context;
        this.topics = topics;
        this.language = language;
    }

    @NonNull
    @Override
    public DuaTopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topiclayout, parent,false);
        return new DuaTopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DuaTopicViewHolder holder, int position) {
        holder.binding.textView3.setText(topics.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //DuaActivity duaActivity = new DuaActivity();
                ((DuaActivity)context).getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.dua_content, DuaDetailFragment.newInstance(topics.get(position), language)).addToBackStack(null).commit();
                //duaActivity.SwitchFragment(R.id.dua_content,DuaTopicFragment.newInstance(result[position],language));
                /*Intent i = new Intent(context,DuaDetail.class);
                i.putExtra("topic",result[position]);
                i.putExtra("lang",language);
                context.startActivity(i);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class DuaTopicViewHolder extends RecyclerView.ViewHolder {
        TopiclayoutBinding binding;
        public DuaTopicViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TopiclayoutBinding.bind(itemView);
        }
    }
}
