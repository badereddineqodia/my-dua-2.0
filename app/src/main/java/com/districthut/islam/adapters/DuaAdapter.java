package com.districthut.islam.adapters;

/**
 * Created by Mian Brothers on 6/7/2017.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.districthut.islam.Activities.DuaActivity;
import com.districthut.islam.utils.DatabaseHelper;
import com.districthut.islam.Fragments.DuaDetailFragment;
import com.mirfatif.noorulhuda.R;


import java.util.ArrayList;

public class DuaAdapter extends BaseAdapter{
    String [] result;
    Context context;
    DuaActivity activity;
    String language;
    Typeface urduFont ;
    private static LayoutInflater inflater=null;
    public DuaAdapter(Context mainActivity, String Category,String Language) {
        // TODO Auto-generated constructor stub
        activity = (DuaActivity)mainActivity;
        context=mainActivity;
        language = Language;
        urduFont = Typeface.createFromAsset(context.getAssets(), "fonts/JameelNooriNastaleeq.ttf");
        final DatabaseHelper dbHeplper = new DatabaseHelper(context);
        try{
            dbHeplper.createDataBase();
            dbHeplper.openDataBase();
        }
        catch (Exception e)
        {

        }
        ArrayList<String> s;
        //int catId = dbHeplper.GetCategoryIdByName(Category);
        if(Category.contains("All Duas") || Category.contains("Semua Doa") || Category.contains("تمام دعائیں"))
            s = dbHeplper.GetAllTopics(language);
        else
            s = dbHeplper.GetTopicsByCategory(Category,language);

        result = s.toArray(new String[s.size()]);
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

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.topiclayout, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView3);
        if(language.contains("ur") || language=="ur") {
            holder.tv.setTypeface(urduFont);
            holder.tv.setTextSize(24);
        }

        holder.tv.setText(result[position]);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //DuaActivity duaActivity = new DuaActivity();
                activity.getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.dua_content, DuaDetailFragment.newInstance(result[position],language)).addToBackStack(null).commit();
                //duaActivity.SwitchFragment(R.id.dua_content,DuaTopicFragment.newInstance(result[position],language));
                /*Intent i = new Intent(context,DuaDetail.class);
                i.putExtra("topic",result[position]);
                i.putExtra("lang",language);
                context.startActivity(i);*/
            }
        });
        return rowView;
    }

}