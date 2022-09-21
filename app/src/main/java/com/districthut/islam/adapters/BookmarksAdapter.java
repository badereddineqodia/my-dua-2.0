package com.districthut.islam.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.districthut.islam.Activities.BookmarkActivity;
import com.districthut.islam.models.Dua;
import com.mirfatif.noorulhuda.R;
import com.districthut.islam.utils.DatabaseHelper;
import com.mirfatif.noorulhuda.databinding.DuadetailviewBinding;

import java.util.ArrayList;

/**
 * Created by Sadmansamee on 7/19/15.
 */
public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.DuaCategoryViewHolder> {

    ArrayList<Dua> duas;
    Context context;
    Typeface urduFont;
    Typeface arabicFont;
    DatabaseHelper dbHeplper;
    String language;
    SharedPreferences SP;
    boolean urdu,english,bahasha,transliter;
    private static LayoutInflater inflater=null;

    public BookmarksAdapter(Context mainActivity, ArrayList<Dua> duas) {
        context=mainActivity;
        dbHeplper = new DatabaseHelper(context);
        SP = PreferenceManager.getDefaultSharedPreferences(context);
        urdu = SP.getBoolean("urduCheck",true);
        english = SP.getBoolean("englishCheck",true);
        bahasha = SP.getBoolean("bahasaCheck",false);
        transliter = SP.getBoolean("transliteration",true);
        try{
            dbHeplper.createDataBase();
            dbHeplper.openDataBase();
        }
        catch (Exception e)
        {

        }

        this.duas = duas;
        //int catId = dbHeplper.GetTopicIdByName(Category);

        arabicFont = Typeface.createFromAsset(context.getAssets(), "fonts/PDMS_Saleem_QuranFont-signed.ttf");
        urduFont = Typeface.createFromAsset(context.getAssets(), "fonts/JameelNooriNastaleeq.ttf");
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public DuaCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.duadetailview, parent, false);
        DuaCategoryViewHolder viewHolder = new DuaCategoryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DuaCategoryViewHolder holder, final int position) {
        final Dua dua = duas.get(position);

        if(dua.bookmark == 1) {
            holder.binding.bookmark.setImageResource(R.drawable.ic_bookmark_filled);
        } else {
            holder.binding.bookmark.setImageResource(R.drawable.ic_bookmark_empty);
        }

        holder.binding.shareDuaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody =
                        String.format(
                                "Arabic: \n %s \n\n Transliteration: \n %s \n\n Urdu: \n %s \n\n English: \n %s \n\n Bahasa Melaye \n  %s \n\n Reference \n %s",
                                dua.arabic,dua.transliteration,dua.urdu,dua.english,dua.bahasa,dua.ref);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        holder.binding.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dua.bookmark == 0) {
                    if(dbHeplper.addDuaToBookmarks(dua.id, 1)) {
                        holder.binding.bookmark.setImageResource(R.drawable.ic_bookmark_filled);
                        dua.bookmark = 1;
                    } else {
                        Toast.makeText(context, "Sorry, Operation Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(dbHeplper.addDuaToBookmarks(dua.id, 0)) {
                        holder.binding.bookmark.setImageResource(R.drawable.ic_bookmark_empty);
                        duas.remove(position);
                        if(duas.size() == 0) {
                            ((BookmarkActivity)context).toggleEmptyState();
                        }
                        notifyDataSetChanged();
                        dua.bookmark = 0;
                    } else {
                        Toast.makeText(context, "Sorry, Operation Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        if(urdu==true) {
            holder.binding.urduDuaDetail.setVisibility(View.VISIBLE);
            holder.binding.urduLbl.setVisibility(View.VISIBLE);
        }
        else {
            holder.binding.urduDuaDetail.setVisibility(View.GONE);
            holder.binding.urduLbl.setVisibility(View.GONE);
        }
        if(english==true) {
            holder.binding.englishDuaDetail.setVisibility(View.VISIBLE);
            holder.binding.englishLbl.setVisibility(View.VISIBLE);
        }
        else {
            holder.binding.englishDuaDetail.setVisibility(View.GONE);
            holder.binding.englishLbl.setVisibility(View.GONE);
        }
        if(transliter==true) {
            holder.binding.transliterationDuaDetail.setVisibility(View.VISIBLE);
            holder.binding.transliterationLbl.setVisibility(View.VISIBLE);
        }
        else {
            holder.binding.transliterationDuaDetail.setVisibility(View.GONE);
            holder.binding.transliterationLbl.setVisibility(View.GONE);
        }
        if(bahasha==true) {
            holder.binding.bahasaDuaDetail.setVisibility(View.VISIBLE);
            holder.binding.bahasaLbl.setVisibility(View.VISIBLE);
        }
        else {
            holder.binding.bahasaDuaDetail.setVisibility(View.GONE);
            holder.binding.bahasaLbl.setVisibility(View.GONE);
        }
        holder.binding.arabicDuaDetail.setTypeface(arabicFont);
        holder.binding.arabicDuaDetail.setText(duas.get(position).arabic);
        holder.binding.englishDuaDetail.setText(duas.get(position).english);
        if(duas.get(position).urdu == null || duas.get(position).urdu == "" || duas.get(position).urdu == " ") {
            holder.binding.urduDuaDetail.setVisibility(View.GONE);
            holder.binding.urduLbl.setVisibility(View.GONE);
        } else {
            holder.binding.urduDuaDetail.setText(duas.get(position).urdu);
            holder.binding.urduDuaDetail.setTypeface(urduFont);
        }

        holder.binding.transliterationDuaDetail.setText(duas.get(position).transliteration);
        holder.binding.bahasaDuaDetail.setText(duas.get(position).bahasa);
        holder.binding.refDuaDetail.setText(duas.get(position).ref);
    }

    @Override
    public long getItemId(int position) {
        //  Surah surah = surahArrayList.get(position);

        //return surahArrayList.get(position).getId();
        return 0;
    }

    public Object getItem(int position) {
        return duas.get(position);
    }

    @Override
    public int getItemCount() {
        return duas.size();
    }




    public class DuaCategoryViewHolder extends RecyclerView.ViewHolder
    {

        DuadetailviewBinding binding;
        public DuaCategoryViewHolder(View rowView) {
            super(rowView);
            binding = DuadetailviewBinding.bind(rowView);
        }



    }

}

