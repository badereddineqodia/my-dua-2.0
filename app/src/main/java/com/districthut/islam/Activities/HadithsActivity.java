package com.districthut.islam.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.districthut.islam.adapters.HadithsAdapter;
import com.districthut.islam.models.HadithsModel;
import com.mirfatif.noorulhuda.databinding.ActivityHadithsBinding;

import java.util.ArrayList;

public class HadithsActivity extends AppCompatActivity {

    ActivityHadithsBinding binding;
    ArrayList<HadithsModel> items;
    HadithsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHadithsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        items=new ArrayList<>();
        items.add(new HadithsModel("Bukhari","B","Imam Bukhari",7563));
        items.add(new HadithsModel("Bukhari","B","Imam Bukhari",7563));
        items.add(new HadithsModel("Bukhari","B","Imam Bukhari",7563));
        items.add(new HadithsModel("Bukhari","B","Imam Bukhari",7563));
        items.add(new HadithsModel("Bukhari","B","Imam Bukhari",7563));
        items.add(new HadithsModel("Bukhari","B","Imam Bukhari",7563));

        adapter=new HadithsAdapter(this,items);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        binding.hadithsRecyler.setLayoutManager(manager);
        binding.hadithsRecyler.setAdapter(adapter);
    }
}