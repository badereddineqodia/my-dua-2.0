package com.districthut.islam.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.mirfatif.noorulhuda.R;
import com.districthut.islam.naat.NaatModel;
import com.districthut.islam.naat.PlayNaatActivity;

import java.util.ArrayList;

/**
 * Created by Sadmansamee on 7/19/15.
 */
public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.MoreViewHolder> {

    ArrayList<String> menuList;
    Context context;
    int [] imageId;
    String language;
    Typeface urduFont ;
    private static LayoutInflater inflater=null;
    String type;
    ArrayList<NaatModel> naats;
    public MoreAdapter(Context mainActivity, ArrayList<String> menuList, int[] menuImages, String lang,String p_type) {
        this.menuList =menuList;
        context=mainActivity;
        type = p_type;
        imageId=menuImages;
        language = lang;
        //urduFont = Typeface.createFromAsset(context.getAssets(), "fonts/JameelNooriNastaleeq.ttf");
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public MoreAdapter(Context mainActivity, ArrayList<String> menuList, String lang,String p_type) {
        this.menuList =menuList;
        context=mainActivity;
        type = p_type;
        language = lang;
        //urduFont = Typeface.createFromAsset(context.getAssets(), "fonts/JameelNooriNastaleeq.ttf");
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public MoreAdapter(Context mainActivity, String lang, String p_type, ArrayList<NaatModel> naats) {
        this.naats = naats;
        context=mainActivity;
        type = p_type;
        language = lang;
        //urduFont = Typeface.createFromAsset(context.getAssets(), "fonts/JameelNooriNastaleeq.ttf");
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.morelistview, parent, false);
        MoreViewHolder viewHolder = new MoreViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MoreViewHolder holder, final int position) {
        if(language.contains("ur") || language=="ur"){
            holder.tv.setTypeface(urduFont);
            holder.tv.setTextSize(24);
        }

        if(type == "naatsdetail") {
            holder.tv.setText(naats.get(position).getTitle());
            holder.distance.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, PlayNaatActivity.class);
                    i.putExtra("title",naats.get(position).getTitle());
                    i.putExtra("url",naats.get(position).getUrl());
                    context.startActivity(i);
                }
            });
        }
            else
        holder.tv.setText(menuList.get(position));

        if(type == "more_menu" || type == "lecs")
        holder.img.setImageResource(imageId[position]);

    }

    @Override
    public long getItemId(int position) {
        //  Surah surah = surahArrayList.get(position);

        //return surahArrayList.get(position).getId();
        return 0;
    }

    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public int getItemCount() {
        if(type == "naatsdetail")
        return naats.size();
        else
            return menuList.size();
    }




    public class MoreViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv;
        TextView distance;
        ImageView img;
        public MoreViewHolder(View rowView) {
            super(rowView);
            tv=(TextView) rowView.findViewById(R.id.textView1);
            distance=(TextView) rowView.findViewById(R.id.distancelbl);
            img=(ImageView) rowView.findViewById(R.id.backgroundImage);
            if(type == "lecs")
                img.setPadding(0,0,0,0);
             else if(type == "naats")
                 img.setVisibility(View.GONE);
        }



    }

}

