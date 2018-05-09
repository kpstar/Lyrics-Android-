package com.example.kpstar.lyricsapp.videos;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.kpstar.lyricsapp.R;
import com.example.kpstar.lyricsapp.customize.YoutubeConfig;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity {

    YouTubePlayerView mYoutubePlayer;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    ToggleButton recodeBtn = null;
    TextView mTitle = null;

    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitialAd;
    private boolean mRecordFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        AdView mAdView = (AdView)findViewById(R.id.adView);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        AdRequest adBannerRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adBannerRequest);
        mAdView.setAdListener(new AdListener() { });

        mTitle = (TextView)findViewById(R.id.titleVideo);
        mTitle.setText(getIntent().getStringExtra("VIDEONAME"));

        mYoutubePlayer = (YouTubePlayerView)findViewById(R.id.player);
        mYoutubePlayer.initialize("AIzaSyDRC_mVh47q0Pqrwi-MudkC2yv8I-GIwaI", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(getIntent().getStringExtra("VIDEOID"));
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        ImageButton backBtn = (ImageButton)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = YoutubeActivity.this.getSharedPreferences("Video", Context.MODE_PRIVATE).edit();
                editor.putBoolean("Interstitial", true);
                editor.apply();
                YoutubeActivity.this.finish();
            }
        });

    }
}
