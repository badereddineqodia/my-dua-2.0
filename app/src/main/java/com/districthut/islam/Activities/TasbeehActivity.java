package com.districthut.islam.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.ActivityTasbeehBinding;

public class TasbeehActivity extends AppCompatActivity {

    ActivityTasbeehBinding binding;
    int i = 0;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Typeface typeface;

    String[] tasbeehat = {"سُبْحَانَ ٱللَّٰهِ","ٱلْحَمْدُ لِلَّٰهِ","اللّٰهُ أَكْبَر","لَا إِلٰهَ إِلَّا ٱلله","يَا حَيُّ يَا قَيُّوْمُ"};
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTasbeehBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("Tasbeeh", MODE_PRIVATE);
        editor = preferences.edit();

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                editor.putInt("counts", i);
                editor.commit();
                binding.counter.setText(String.valueOf(i));
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.arabic.setText(tasbeehat[0]);
        typeface = Typeface.createFromAsset(getAssets(),"fonts/arabic.ttf");

        binding.arabic.setTypeface(typeface);
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index < tasbeehat.length-1) {
                    index++;
                    binding.arabic.setText(tasbeehat[index]);
                }
            }
        });

        binding.previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index > 0) {
                    index--;
                    binding.arabic.setText(tasbeehat[index]);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.refresh) {
            i = 0;
            editor.putInt("counts", i);
            editor.commit();
            binding.counter.setText(String.valueOf(i));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void resetTasbeeh(View view) {
        i = 0;
        editor.putInt("counts", i);
        editor.commit();
        binding.counter.setText(String.valueOf(i));
    }

    @Override
    protected void onResume() {
        super.onResume();
        i = preferences.getInt("counts",0);
        binding.counter.setText(String.valueOf(i));
    }
}