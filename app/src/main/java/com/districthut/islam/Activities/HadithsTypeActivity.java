package com.districthut.islam.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.districthut.islam.adapters.HadithsTypeAdapter;
import com.districthut.islam.models.HadithsTypeModel;
import com.mirfatif.noorulhuda.databinding.ActivityHadithsTypeBinding;

import java.util.ArrayList;

public class HadithsTypeActivity extends AppCompatActivity {

    ActivityHadithsTypeBinding binding;
    ArrayList<HadithsTypeModel> items;
    HadithsTypeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHadithsTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        items = new ArrayList<>();
        items.add(new HadithsTypeModel(1,1,"Revelation","1 to 7"));
        items.add(new HadithsTypeModel(2,2,"Belief","8 to 58"));
        items.add(new HadithsTypeModel(3,3,"Knowledge","59 to 134"));
        items.add(new HadithsTypeModel(4,4,"Ablutions(Wudu')","135 to 247"));
        items.add(new HadithsTypeModel(5,5,"Bathing(Ghusl)","248 to 293"));
        items.add(new HadithsTypeModel(6,6,"Mentrual Periods","294 to 333"));

        adapter=new HadithsTypeAdapter(this,items);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        binding.hadithsTypeRecyler.setLayoutManager(manager);
        binding.hadithsTypeRecyler.setAdapter(adapter);
    }
}