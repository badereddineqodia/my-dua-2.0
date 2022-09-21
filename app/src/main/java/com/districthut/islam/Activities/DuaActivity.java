package com.districthut.islam.Activities;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.districthut.islam.Fragments.DuaCategoriesFragment;
import com.districthut.islam.Fragments.DuaDetailFragment;
import com.districthut.islam.utils.AppManager;

import com.mirfatif.noorulhuda.R;

public class DuaActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppManager.checkTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua);





        String topic = getIntent().getStringExtra("topic");
        String lang = getIntent().getStringExtra("lang");
        if(topic == "" || topic == null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.dua_content, new DuaCategoriesFragment().newInstance());
            transaction.commit();
        }else
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.dua_content, new DuaDetailFragment().newInstance(topic,lang));
            transaction.commit();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookmark_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bookmark:
                startActivity(new Intent(DuaActivity.this, BookmarkActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackStackChanged(){
        if(getSupportFragmentManager() != null)
            if(getSupportFragmentManager().getBackStackEntryCount()==0)
            getSupportActionBar().setTitle("Duas");
    }


    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        if(getSupportFragmentManager().getBackStackEntryCount()>0)
            getSupportFragmentManager().popBackStack();
        else
            finish();
        return true;
    }


}
