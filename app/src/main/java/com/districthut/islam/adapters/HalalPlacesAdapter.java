package com.districthut.islam.adapters;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.districthut.islam.Activities.HalalPlacesActivity;
import com.districthut.islam.models.Place;
import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.SampleLayoutOfHalalPlacesBinding;

import java.util.ArrayList;

public class HalalPlacesAdapter extends RecyclerView.Adapter<HalalPlacesAdapter.MyViewHolder>{

    Context context;
    ArrayList<Place> items;
    String type;

    public HalalPlacesAdapter(Context context, ArrayList<Place> items, String type) {
        this.context = context;
        this.items = items;
        this.type = type;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_layout_of_halal_places, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Place model=items.get(position);
        holder.binding.halalPlacesName.setText(model.getName());

        if(type.equals("restaurant")) {
            double distance = distance(
                    ((HalalPlacesActivity)context).location.getLatitude(),
                    ((HalalPlacesActivity)context).location.getLongitude(),
                    model.getLatitude(),
                    model.getLongitude());

            Location location1 = new Location("");
            location1.setLatitude(model.getLatitude());
            location1.setLongitude(model.getLongitude());

//            ((HalalPlacesActivity)context).location.distanceTo(location1);


            double distanceInKM = distance/1000;

            holder.binding.halalPlacesDistance.setText(String.format("%.1f KM",distanceInKM));

            if(model.getRating() > 0) {
                holder.binding.ratingBar.setVisibility(View.VISIBLE);
                holder.binding.ratingBar.setRating((float)model.getRating());
                holder.binding.ratings.setText(String.format("(%d reviews)",model.getTotal_user_ratings()));
            } else {
                holder.binding.ratings.setVisibility(View.GONE);
                //holder.binding.ratingBar.setVisibility(View.GONE);
            }

            if(model.isOpen()) {
                holder.binding.status.setVisibility(View.VISIBLE);
                holder.binding.status.setText("OPEN");
                holder.binding.status.setBackgroundColor(context.getColor(R.color.green));
            } else {
                holder.binding.status.setVisibility(View.VISIBLE);
                holder.binding.status.setText("CLOSED");
                holder.binding.status.setBackgroundColor(context.getColor(R.color.reddish_pink));
            }
        } else {
            double distance = distance(
                    ((HalalPlacesActivity)context).location.getLatitude(),
                    ((HalalPlacesActivity)context).location.getLongitude(),
                    model.getLatitude(),
                    model.getLongitude());

            Location location1 = new Location("");
            location1.setLatitude(model.getLatitude());
            location1.setLongitude(model.getLongitude());

//            ((HalalPlacesActivity)context).location.distanceTo(location1);


            double distanceInKM = distance/1000;

            holder.binding.halalPlacesDistance.setText(String.format("%.1f KM",distanceInKM));

            holder.binding.ratingBar.setVisibility(View.GONE);
            holder.binding.ratings.setVisibility(View.GONE);
            holder.binding.status.setVisibility(View.GONE);
        }

//        holder.description.setText(model.getPlaceDescription());
//        holder.distance.setText(model.getPlaceDistance());
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SampleLayoutOfHalalPlacesBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleLayoutOfHalalPlacesBinding.bind(itemView);
        }
    }
}
