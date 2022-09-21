package com.districthut.islam.Activities;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.mirfatif.noorulhuda.R;

public class About extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        LinearLayout facebook = (LinearLayout) findViewById(R.id.facebookLink);
        LinearLayout twitter = (LinearLayout) findViewById(R.id.twitterLink);
        LinearLayout website = (LinearLayout) findViewById(R.id.websiteLink);
        facebook.setOnClickListener(this);
        twitter.setOnClickListener(this);
        website.setOnClickListener(this);

        LinearLayout review = (LinearLayout) findViewById(R.id.reviewLink);
        review.setOnClickListener(this);
        LinearLayout share = (LinearLayout) findViewById(R.id.shareLink);
        share.setOnClickListener(this);

        if(FirebaseRemoteConfig.getInstance().getBoolean("contribution")) {
            findViewById(R.id.contribution).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.contribution).setVisibility(View.GONE);
        }

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view)
    {
        String URL = "" ;
        int id = view.getId();
        switch (id){
            case R.id.facebookLink:
                    URL = "https://www.facebook.com/mianasadalii";
                break;
            case R.id.twitterLink:
                URL = "https://twitter.com/mianasadali";
                break;
            case R.id.websiteLink:
                URL = "http://www.mianasad.com";
                break;
            case R.id.reviewLink:
                URL = "https://play.google.com/store/apps/details?id=com.mianasad.myislam";
                break;
            case R.id.shareLink:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "There\\'s an app where you just select your emotions and " +
                        "current feelings (e.g ANGRY,CONFIDENT,INSECURE etc. and it gives " +
                        "you an Ayat or Surah (in ARABIC, URDU &amp; ENGLISH) that " +
                        "correlates with it.\n" + "(Available on Playstore)\n" +
                        "https://play.google.com/store/apps/details?id=com.mianasad.myislam\n\n"+
                        "Pass it to everyone you know. It\\s Awesome!";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivity(browserIntent);
    }
}
