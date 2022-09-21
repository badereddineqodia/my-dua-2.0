package com.districthut.islam.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.districthut.islam.utils.AppManager;
import com.mirfatif.noorulhuda.databinding.ActivityShahadaBinding;

public class ShahadaActivity extends AppCompatActivity {
    ActivityShahadaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppManager.checkTheme(this);
        super.onCreate(savedInstanceState);
        binding = ActivityShahadaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}