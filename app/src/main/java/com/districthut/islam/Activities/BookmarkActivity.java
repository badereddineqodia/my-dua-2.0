package com.districthut.islam.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.districthut.islam.adapters.BookmarksAdapter;
import com.districthut.islam.models.Dua;
import com.districthut.islam.utils.AppManager;
import com.districthut.islam.utils.DatabaseHelper;
import com.mirfatif.noorulhuda.databinding.ActivityBookmarkBinding;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {

    ActivityBookmarkBinding binding;
    BookmarksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppManager.checkTheme(this);
        super.onCreate(savedInstanceState);
        binding = ActivityBookmarkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Dua> duas = new ArrayList<>();
        adapter = new BookmarksAdapter(this, duas);
        binding.recyclerView.setAdapter(adapter);
        DatabaseHelper helper = new DatabaseHelper(this);
        try {
            helper.createDataBase();
            helper.openDataBase();

            duas.addAll(helper.GetBookmarkedDuas());
            if(duas.size() == 0)
                toggleEmptyState();
            adapter.notifyDataSetChanged();


        } catch (IOException e) {
            Log.e("error", e.getLocalizedMessage());
        } catch (SQLException e) {
            Log.e("error", e.getLocalizedMessage());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void toggleEmptyState() {
        if(binding.emptyState.getVisibility() == View.VISIBLE)
            binding.emptyState.setVisibility(View.GONE);
        else
            binding.emptyState.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}