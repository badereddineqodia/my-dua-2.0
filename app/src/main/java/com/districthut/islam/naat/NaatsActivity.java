package com.districthut.islam.naat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.mirfatif.noorulhuda.R;


public class NaatsActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naats);

        String QUERY = "";
        if(getIntent().hasExtra("query"))
        QUERY = getIntent().getStringExtra("query");
        if(QUERY.isEmpty()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, new Naat_Main_Fragment()).commit();
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            NaatKhawa_NaatsFragment naatkhawafragment = new NaatKhawa_NaatsFragment();
            Bundle args = new Bundle();
            args.putBoolean("search",true);
            args.putString("query",QUERY);
            naatkhawafragment.setArguments(args);
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, naatkhawafragment).commit();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onBackStackChanged(){
        if(getSupportFragmentManager().getBackStackEntryCount()==0)
            getSupportActionBar().setTitle("Naats");
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
