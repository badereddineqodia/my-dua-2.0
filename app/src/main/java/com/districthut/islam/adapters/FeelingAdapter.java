package com.districthut.islam.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.districthut.islam.models.Dua;
import com.mirfatif.noorulhuda.R;

import java.util.ArrayList;
import java.util.Random;

public class FeelingAdapter extends RecyclerView.Adapter<FeelingAdapter.FeelingViewHolder> {

    ArrayList<Dua> duas;
    Context context;
    Typeface arabicFont, urduFont;

    public FeelingAdapter(ArrayList<Dua> duas, Context context) {
        this.duas = duas;
        this.context = context;
        arabicFont = Typeface.createFromAsset(context.getAssets(), "fonts/PDMS_Saleem_QuranFont-signed.ttf");
        urduFont = Typeface.createFromAsset(context.getAssets(), "fonts/JameelNooriNastaleeq.ttf");
    }

    @NonNull
    @Override
    public FeelingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_dua, null);
        return new FeelingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeelingViewHolder holder, int position) {
        Dua dua = duas.get(position);
        holder.feeling.setText(dua.title);
        holder.ayat.setText(dua.arabic);
        holder.urdu.setText(dua.urdu);
        holder.english.setText(dua.english);
        holder.ref.setText(dua.ref);

//        if(holder.ayat.getLineCount() > 3) {
//            String ayat = holder.ayat.getText().toString();
//            ayat = ayat.substring(0, ayat.length()-10) + "...";
//            holder.ayat.setText(ayat);
//        }

        holder.background.setBackgroundColor(getColor());
    }

    public int getColor(){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(130), rnd.nextInt(70), rnd.nextInt(100));
        return color;
    }

    @Override
    public int getItemCount() {
        return duas.size();
    }

    public class FeelingViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout background;
        private TextView feeling, ayat, ref, read, urdu, english;
        private ImageView facebook, whatsapp, twitter, instagram;

        public FeelingViewHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.background);
            feeling = itemView.findViewById(R.id.feeling);
            ayat = itemView.findViewById(R.id.ayat);
            urdu = itemView.findViewById(R.id.urdu);
            english = itemView.findViewById(R.id.english);
            ref = itemView.findViewById(R.id.ref);
            read = itemView.findViewById(R.id.read);
            facebook = itemView.findViewById(R.id.facebook);
            whatsapp = itemView.findViewById(R.id.whatsapp);
            twitter = itemView.findViewById(R.id.twitter);
            instagram = itemView.findViewById(R.id.instagram);

            ayat.setTypeface(arabicFont);
            urdu.setTypeface(urduFont);
        }
    }
}
