package com.districthut.islam.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mirfatif.noorulhuda.databinding.ActivityHadithsDetailBinding;

public class HadithsDetailActivity extends AppCompatActivity {

    ActivityHadithsDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHadithsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}