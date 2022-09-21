package com.districthut.islam.adapters;

/**
 * Created by Mian Brothers on 11/12/2017.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirfatif.noorulhuda.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CustomGrid extends BaseAdapter{
    private Context mContext;
    private final String[] web;
    //private final int[] Imageid;
    private ArrayList<String> images = null;

    public CustomGrid(Context c,String[] web,ArrayList<String> images) {
        mContext = c;
        this.web = web;
        this.images = images;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {

            //grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            //imageView.setImageResource(Imageid[position]);
        } else {
            grid = (View) convertView;
        }
        TextView textView = (TextView) grid.findViewById(R.id.grid_text);
        ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
        textView.setText(web[position]);
        try {
            // get input stream
            InputStream ims = mContext.getAssets().open(images.get(position));
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imageView.setImageDrawable(d);
        }
        catch(IOException ex) {

        }

        return grid;
    }
}