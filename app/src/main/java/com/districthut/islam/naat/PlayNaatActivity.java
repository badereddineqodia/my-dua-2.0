package com.districthut.islam.naat;

import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.mirfatif.noorulhuda.R;
import com.districthut.islam.utils.DatabaseHelper;
import com.districthut.islam.naat.util.Constants;
import com.districthut.islam.naat.util.SDCardManager;



import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayNaatActivity extends AppCompatActivity implements JcPlayerManagerListener {


    JcPlayerView player;
    Button downloadBtn;
    boolean check = false;
    String naatsPath = "";
    String url;
    boolean media_available;
    ProgressBar progressBar;
    List<JcAudio> list;
    String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_naat);


        naatsPath = this.getExternalFilesDir(null).getPath() + "/";
        if(SDCardManager.isSdCardWritable()) {
            SDCardManager.CreateFolders(this.getExternalFilesDir(null).getPath() +  "/" + "My Dua" + "/" + "NAATS");
        } else {

        }
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        getSupportActionBar().setTitle(title);
        if(SDCardManager.isSdCardReadable()){
            if (SDCardManager.getStatus(naatsPath + title +".mp3").equals(Constants.MEDIA_AVAILABLE)){
                media_available = true;
                url = naatsPath + title + ".mp3";
            }
        }



        player = (JcPlayerView) findViewById(R.id.jcplayer);

        downloadBtn = (Button)findViewById(R.id.downloadNaatBtn);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckFile(title,url);
            }
        });



        list = new ArrayList<>();
        if(media_available)
            list.add(JcAudio.createFromFilePath(title,url));
            else
            list.add(JcAudio.createFromURL(title,url));
        //list.add();
        player.initPlaylist(list,this);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    boolean CheckFile(final String naatTitle,final String naatUrl){
        if (SDCardManager.isSdCardWritable()) {
            //SDCardManager.CreateFolders(Constants.TRANSLATIONS_PATH + "/" + reciter);

            final String filename = naatTitle + ".mp3";
            if (SDCardManager.getStatus(naatsPath + filename).equals(Constants.MEDIA_NOT_AVAILABLE)) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

                // Setting Dialog Title
                alertDialog.setTitle("Download Naat");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure to download this naat?");

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Download(url,naatsPath,filename);
                        //MediaDownloadManager.init(PlayNaatActivity.this);
                        //MediaDownloadManager.startDownload(naatTitle,naatUrl,naatsPath + filename);
                        check = true;
                        //Toast.makeText(PlayNaatActivity.this, "Downloading started...", Toast.LENGTH_SHORT).show();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        check = false;
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            } else {
                check = true;
            }
        }
        return check;
    }
    int downloadId;
    void Download(String url,final String dir,final String filename){
        Log.e("aa",url + "\n" + dir + "\n" + filename);
        downloadId = PRDownloader.download(url, dir, filename)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        Toast.makeText(PlayNaatActivity.this, "Download started.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        //progress.currentBytes;
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                        progressBar.setProgress((int) progressPercent);
                        downloadBtn.setEnabled(false);
                        //textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                        //progressBarOne.setIndeterminate(false);
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        DatabaseHelper helper = new DatabaseHelper(PlayNaatActivity.this);
                        try {
                            helper.createDataBase();
                            helper.openDataBase();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        if(helper.UpdateNaats(title) == true){
                            Toast.makeText(PlayNaatActivity.this, "Naat Downloaded", Toast.LENGTH_SHORT).show();
                            Toast.makeText(PlayNaatActivity.this, "Saved to naats.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Error error) {

                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
            finish();
        return true;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        player.kill();
    }

    @Override
    public void onPause(){
        super.onPause();
        player.createNotification(R.drawable.logo);
    }


    @Override
    public void onCompletedAudio() {

    }

    @Override
    public void onContinueAudio(JcStatus jcStatus) {

    }

    @Override
    public void onJcpError(Throwable throwable) {

    }

    @Override
    public void onPaused(JcStatus jcStatus) {

    }

    @Override
    public void onPlaying(JcStatus jcStatus) {

    }

    @Override
    public void onPreparedAudio(JcStatus jcStatus) {

    }

    @Override
    public void onStopped(JcStatus jcStatus) {

    }

    @Override
    public void onTimeChanged(JcStatus jcStatus) {

    }
}
