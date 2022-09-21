package com.districthut.islam.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.districthut.islam.utils.AppManager;
import com.mirfatif.noorulhuda.databinding.ActivityLiveMakkahBinding;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;

public class LiveMakkahActivity extends AppCompatActivity {

    ActivityLiveMakkahBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppManager.checkTheme(this);
        super.onCreate(savedInstanceState);
        binding = ActivityLiveMakkahBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getLifecycle().addObserver(binding.youtubePlayerView);
        IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                .controls(0)
                .build();
        binding.youtubePlayerView.setEnableAutomaticInitialization(false);
        binding.youtubePlayerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "jkGhG6h092Y";
                youTubePlayer.loadVideo(videoId, 0);

                DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(binding.youtubePlayerView, youTubePlayer);
                binding.youtubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());
            }
        },true,iFramePlayerOptions);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.youtubePlayerView.release();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}