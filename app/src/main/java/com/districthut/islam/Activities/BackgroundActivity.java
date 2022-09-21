package com.districthut.islam.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.districthut.islam.adapters.BackgroundAdapter;
import com.districthut.islam.models.BackgroundModel;
import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.ActivityBackgroundBinding;

import java.util.ArrayList;

public class BackgroundActivity extends AppCompatActivity {

    ActivityBackgroundBinding binding;
    ArrayList<BackgroundModel> backgroundItems;
    BackgroundAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBackgroundBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backgroundItems=new ArrayList<>();
        backgroundItems.add(new BackgroundModel(R.drawable.demo_background_pic));
        backgroundItems.add(new BackgroundModel(R.drawable.background_img2));
        backgroundItems.add(new BackgroundModel(R.drawable.backgroundimg6));
        backgroundItems.add(new BackgroundModel(R.drawable.background_img3));
        backgroundItems.add(new BackgroundModel(R.drawable.backgroundimg7));
        backgroundItems.add(new BackgroundModel(R.drawable.backgroundimg4));
        backgroundItems.add(new BackgroundModel(R.drawable.backgroundimg5));



        backgroundItems.add(new BackgroundModel(R.drawable.demo_background_pic));
        backgroundItems.add(new BackgroundModel(R.drawable.background_img3));
        backgroundItems.add(new BackgroundModel(R.drawable.background_img2));
        backgroundItems.add(new BackgroundModel(R.drawable.background_img2));
        backgroundItems.add(new BackgroundModel(R.drawable.backgroundimg5));

        adapter=new BackgroundAdapter(this,backgroundItems);
        binding.backgroundImagesRecyler.setLayoutManager(new GridLayoutManager(this, 3));
        binding.backgroundImagesRecyler.setAdapter(adapter);

    }
}