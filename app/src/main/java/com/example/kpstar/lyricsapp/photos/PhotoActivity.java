package com.example.kpstar.lyricsapp.photos;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.kpstar.lyricsapp.R;
import com.example.kpstar.lyricsapp.customize.WSDatabase;
import com.example.kpstar.lyricsapp.customize.song_item;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.io.InputStream;

public class PhotoActivity extends AppCompatActivity {

    ImageView mImgView = null;
    private InputStream iStream = null;
    private WSDatabase database;
    private song_item songItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mImgView = (ImageView)findViewById(R.id.imgPhoto);
        AdView mAdView = (AdView)findViewById(R.id.adView);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        AdRequest adBannerRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adBannerRequest);
        mAdView.setAdListener(new AdListener() { });


        database = new WSDatabase(this);
        database.open();

//        int mId = getIntent().getIntExtra("ID", 0);
//        if (mId > 0) {
//            songItem = database.getSongItem(mId);
//            getSupportActionBar().setTitle(songItem.getTitle());
//        }

        String title = getIntent().getStringExtra("PHOTONAME");
        getSupportActionBar().setTitle(title);

        String url = getIntent().getStringExtra("PHOTOS");

        try {
            iStream = this.getAssets().open(url);
            Drawable drawable = Drawable.createFromStream(iStream, null);
            mImgView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
