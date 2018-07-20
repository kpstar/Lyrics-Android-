package com.example.kpstar.lyricsapp.songs;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.kpstar.lyricsapp.R;
import com.example.kpstar.lyricsapp.customize.WSDatabase;
import com.example.kpstar.lyricsapp.customize.song_item;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class PlayActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private MediaPlayer mediaPlayer;
    private song_item songItem;
    SeekBar seekBar;
    ToggleButton normalBtn, fastBtn, slowBtn;
    private double timeElapsed = 0, finalTime = 0;
    private Handler durationHandler = new Handler();
    private float playbackSpeed = 1f;
    private boolean mRecordFlag = false;
    private boolean mFavouriteFlag = false;
    private WSDatabase database;
    private MediaRecorder recorder;
    private InputStream iStream = null;
    private ScrollView scrollView = null;
    private CountDownTimer countDownTimer = null;
    private Boolean isLyrics = false;
    int m = 0;
    FileInputStream inputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Lyrics/Records/");

        if (!file.exists()) {
            file.mkdirs();
        }

        AdView mAdView = (AdView)findViewById(R.id.adView);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        AdRequest adBannerRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adBannerRequest);
        mAdView.setAdListener(new AdListener() { });


        database = new WSDatabase(this);
        database.open();

        int mId = getIntent().getIntExtra("ID", 0);
        if (mId > 0) {
            songItem = database.getSongItem(mId);
            getSupportActionBar().setTitle(songItem.getTitle());
        }

        ImageView mImgView = (ImageView)findViewById(R.id.imageBand);

        try {
            iStream = this.getAssets().open(songItem.getPhotoname());
            Drawable drawable = Drawable.createFromStream(iStream, null);
            mImgView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        scrollView = (ScrollView)findViewById(R.id.scrollLyrics);



        TextView txtView = (TextView)findViewById(R.id.textLyrics);
        txtView.setText(songItem.getLyrics());

        mediaPlayer = new MediaPlayer();
        AssetFileDescriptor afd = null;
        try {
            afd = this.getAssets().openFd(songItem.getSongurl());
            inputStream = afd.createInputStream();
            mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mediaPlayer.prepare();
        } catch (IOException e) {
            Toast.makeText(this, "No Audio File", Toast.LENGTH_SHORT).show();
            this.finish();
            e.printStackTrace();
        }

        seekBar = (SeekBar) findViewById(R.id.playPgs);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(this);

        ToggleButton mBtnPlay = (ToggleButton)findViewById(R.id.playBtn);
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                else {
                    mediaPlayer.start();
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    durationHandler.postDelayed(updateSeekBarTime, 100);
                }
            }
        });

        normalBtn = (ToggleButton)findViewById(R.id.normalspeedBtn);
        fastBtn = (ToggleButton)findViewById(R.id.speedupBtn);
        slowBtn = (ToggleButton)findViewById(R.id.speeddownBtn);
        isLyrics = false;

        normalBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isLyrics) {
                    countDownTimer.cancel();
                    countDownTimer.onFinish();
                    countDownTimer = null;
                    isLyrics = false;
                    return;
                }
                playbackSpeed = 1f;
                isLyrics = true;
                countDownTimer = new CountDownTimer(100000 , 1) {

                    public void onTick(long millisUntilFinished) {
                        scrollView.scrollTo(0, (int)(scrollView.getScrollY()+playbackSpeed));
                    }

                    public void onFinish() {
                    }

                }.start();
            }
        });

        fastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLyrics) {
                    return;
                }
                playbackSpeed += 1f;
                countDownTimer.start();
            }
        });

        slowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLyrics) {
                    return;
                }
                if (playbackSpeed < 2f) {
                    return;
                }
                playbackSpeed -= 1f;
                countDownTimer.start();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lyrics, menu);
        mFavouriteFlag = songItem.getFavorite();
        MenuItem item = menu.findItem(R.id.favouAction);
        if (mFavouriteFlag) {
            item.setIcon(R.drawable.favouritetwo);
        } else {
            item.setIcon(R.drawable.favouriteone);
        }
        return true;
    }


    //handler to change seekBarTime

    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            timeElapsed = mediaPlayer.getCurrentPosition();
            seekBar.setProgress((int) timeElapsed);
            double timeRemaining = finalTime - timeElapsed;
            durationHandler.postDelayed(this, 100);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                if (mFavouriteFlag == true) {
                    database.setFavourite(songItem.getID(), 1);
                } else {
                    database.setFavourite(songItem.getID(), 0);
                }
                PlayActivity.this.finish();
                break;
            case R.id.favouAction:
                if (mFavouriteFlag == false) {
                    mFavouriteFlag = true;
                    item.setIcon(R.drawable.favouritetwo);
                    Toast.makeText(this, "Favourite Selected", Toast.LENGTH_SHORT).show();
                } else {
                    mFavouriteFlag = false;
                    item.setIcon(R.drawable.favouriteone);
                    Toast.makeText(this, "Favourite Unselected", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.recordAction:
                if (mRecordFlag == false) {
                    mRecordFlag = true;
                    item.setIcon(R.drawable.recordtwo);
                    startRecording();
                } else {
                    mRecordFlag = false;
                    item.setIcon(R.drawable.recordone);
                    stopRecording();
                }
                break;
        }
        return true;
    }

    public void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        String audioFilePath = Environment.getExternalStorageDirectory().getPath()+"/Lyrics/Records/";
        Date today_time = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_h_m_s");
        String formattedDate = df.format(today_time);
        audioFilePath += songItem.getTitle() + "_" + formattedDate +".aac";
        recorder.setOutputFile(audioFilePath);
        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e("Log", "prepare() failed");
        }
    }

    public void stopRecording() {
        recorder.stop();
        recorder.reset();
        recorder.release();
    }

    @Override
    public void onBackPressed() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        if (mFavouriteFlag == true) {
            database.setFavourite(songItem.getID(), 1);
        } else {
            database.setFavourite(songItem.getID(), 0);
        }
        super.onBackPressed();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(mediaPlayer != null && fromUser){
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
