package com.districthut.islam.Activities.Quran.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.districthut.islam.Activities.Quran.model.Surah;

//import com.districthut.islam.Models.quran.Surah;
import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.RowSurahBinding;
import com.mirfatif.noorulhuda.quran.MainActivity;

import java.io.File;
import java.util.ArrayList;

import alirezat775.lib.downloader.Downloader;
import alirezat775.lib.downloader.core.OnDownloadListener;


/**
 * Created by Sadmansamee on 7/19/15.
 */
public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahViewHolder> {


    private ArrayList<Surah> surahArrayList;
    private Context context;
    Typeface arabic_font;
    AlertDialog downloadingDialog;
    Downloader downloader;


    public SurahAdapter(ArrayList<Surah> surahArrayList, Context context) {
        this.surahArrayList = surahArrayList;
        this.context = context;
        arabic_font = Typeface.createFromAsset(context.getAssets(),  "fonts/arabic.ttf");
        downloadingDialog = new AlertDialog.Builder(context)
                .setTitle("Downloading")
                .setMessage(R.string.download_format)
                .setCancelable(false)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        downloader.cancelDownload();
                    }
                })
                .create();
    }



    @Override
    public SurahViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_surah, parent, false);
        SurahViewHolder viewHolder = new SurahViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SurahViewHolder holder, int position) {

        final Surah surah = surahArrayList.get(position);
//        holder.surah_idTextView.setText(Long.toString(surah.getId()) + ".");
        //arabic_font = Typeface.createFromAsset(context.getAssets(),  "fonts/symbols.ttf");

        holder.binding.surahId.setText(String.valueOf(surah.getId()));
        holder.binding.englishSurahTitleTv.setText(surah.getNameTranslate());
        holder.binding.arabicSurahTitleTv.setText(surah.getSurah_font());
        if(surah.getFont_type() == 1) {
            holder.binding.arabicSurahTitleTv.setTypeface(ResourcesCompat.getFont(context,R.font.surah_font));
        } else if(surah.getFont_type() == 2) {
            holder.binding.arabicSurahTitleTv.setTypeface(ResourcesCompat.getFont(context,R.font.surah_font_2));
        } else if(surah.getFont_type() == 3) {
            holder.binding.arabicSurahTitleTv.setTypeface(ResourcesCompat.getFont(context,R.font.surah_font_3));
        } else if(surah.getFont_type() == 4) {
            holder.binding.arabicSurahTitleTv.setTypeface(ResourcesCompat.getFont(context,R.font.surah_font_4));
        }
        holder.binding.englishSurahTitleDescTv.setText(surah.getType());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("SURAH_NUM",surah.getId().intValue());
                i.putExtra("AAYAH_NUM",0);
                context.startActivity(i);

            }
        });

    }

    @Override
    public long getItemId(int position) {
        //  Surah surah = surahArrayList.get(position);

        return surahArrayList.get(position).getId();
    }

    public Object getItem(int position) {

        return surahArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return surahArrayList.size();
    }




    public class SurahViewHolder extends RecyclerView.ViewHolder
    {

        RowSurahBinding binding;

        public SurahViewHolder(View view) {
            super(view);
            binding = RowSurahBinding.bind(view);
        }



    }



}

