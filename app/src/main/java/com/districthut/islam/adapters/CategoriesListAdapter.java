package com.districthut.islam.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.districthut.islam.Activities.DuaActivity;
import com.districthut.islam.utils.DatabaseHelper;
import com.districthut.islam.Fragments.DuaDetailFragment;
import com.districthut.islam.Fragments.DuaTopicFragment;
import com.mirfatif.noorulhuda.R;
import com.districthut.islam.models.SampleSearchModel;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;


/**
 * Created by Mian Brothers on 10/28/2017.
 */

public class CategoriesListAdapter extends BaseAdapter {
    String [] result;
    Context context;
    int [] imageId;
    String language;
    Typeface urduFont ;
    private static LayoutInflater inflater=null;
    DatabaseHelper dbHeplper;
    private ArrayList<SampleSearchModel> AllSearchTopics;

    public CategoriesListAdapter(Context mainActivity, String[] prgmNameList, int[] prgmImages,String lang) {
        // TODO Auto-generated constructor stub

        result=prgmNameList;
        context=mainActivity;
        dbHeplper = new DatabaseHelper(context);
        try{
            dbHeplper.createDataBase();
            dbHeplper.openDataBase();
        }
        catch (Exception e)
        {

        }
        imageId=prgmImages;
        language = lang;
        urduFont = Typeface.createFromAsset(context.getAssets(), "fonts/JameelNooriNastaleeq.ttf");
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.customlistview, null);

        holder.tv=(TextView) rowView.findViewById(R.id.textView1);

        if(language.contains("ur") || language=="ur"){
            holder.tv.setTypeface(urduFont);
            holder.tv.setTextSize(24);
        }

        holder.img=(ImageView) rowView.findViewById(R.id.backgroundImage);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(result[position].contains("Search Duas") || result[position].contains("Carian Pray") || result[position].contains("دعا تلاش کریں")){

                    AllSearchTopics = dbHeplper.GetAllSearchTopics(language);
                    new SimpleSearchDialogCompat(context, "Search...",
                            "What are you looking for...?", null, AllSearchTopics,
                            new SearchResultListener<SampleSearchModel>() {
                                @Override
                                public void onSelected(BaseSearchDialogCompat dialog,
                                                       SampleSearchModel item, int position) {
                                    DuaActivity d = (DuaActivity)context;
                                    d.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.dua_content, DuaDetailFragment.newInstance(item.getTitle(),language)).addToBackStack(null).commit();
                                    dialog.dismiss();
                                }
                            }).show();
                } else
                {
                    DuaActivity d = (DuaActivity)context;
                    d.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.dua_content, DuaTopicFragment.newInstance(result[position],language)).addToBackStack(null).commit();
                }


                //duaActivity.SwitchFragment(R.id.dua_content,DuaTopicFragment.newInstance(result[position],language));
                /*
                Intent i = new Intent(v.getContext(),Duas.class);
                i.putExtra("cat",result[position]);
                i.putExtra("lang",language);
                v.getContext().startActivity(i);*/
            }
        });

        return rowView;
    }
}
