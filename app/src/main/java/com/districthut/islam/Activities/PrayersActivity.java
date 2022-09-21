package com.districthut.islam.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.mirfatif.noorulhuda.databinding.ActivityPrayersBinding;
import com.districthut.islam.prayer.fragments.PrayerTimesFragment;

public class PrayersActivity extends AppCompatActivity {

    ActivityPrayersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrayersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(binding.getRoot().getId(), PrayerTimesFragment.newInstance(0, HomeActivity.location));
        transaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}