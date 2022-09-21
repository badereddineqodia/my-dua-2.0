package com.districthut.islam.Activities.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.SampleEventsRowBinding;

import java.util.ArrayList;

public class DatesAdapter extends RecyclerView.Adapter<DatesAdapter.DatesViewHolder> {

    Context context;
    ArrayList<Event> events;

    public DatesAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public DatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DatesViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_events_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DatesViewHolder holder, int position) {
        Event event = events.get(position);
        holder.binding.eventName.setText(event.getEventName());
        holder.binding.hijriDate.setText(event.getEventDate());
        holder.binding.georgianDate.setText(event.getEventGeorgianDate());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class DatesViewHolder extends RecyclerView.ViewHolder {

        SampleEventsRowBinding binding;

        public DatesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleEventsRowBinding.bind(itemView);
        }
    }
}
