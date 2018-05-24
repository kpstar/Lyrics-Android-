package com.example.kpstar.lyricsapp;

import android.Manifest;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.kpstar.lyricsapp.customize.WSDatabase;
import com.example.kpstar.lyricsapp.customize.song_item;
import com.example.kpstar.lyricsapp.fragment.AboutusFragment;
import com.example.kpstar.lyricsapp.fragment.ConcertphotosFragment;
import com.example.kpstar.lyricsapp.fragment.FacebookFragment;
import com.example.kpstar.lyricsapp.fragment.FavouriteFragment;
import com.example.kpstar.lyricsapp.fragment.GoogleFragment;
import com.example.kpstar.lyricsapp.fragment.RateusFragment;
import com.example.kpstar.lyricsapp.fragment.RecordingFragment;
import com.example.kpstar.lyricsapp.fragment.SongsFragment;
import com.example.kpstar.lyricsapp.fragment.VideosFragment;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MenuItem searchMenuItem;
    public SearchView searchView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};

        if (!EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermissions(permissions, 0);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment selectedFragment = new SongsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, selectedFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public SearchView getSearchView() {
        return searchView;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment selectedFragment = null;
        if (id == R.id.nav_songs) {
            getSupportActionBar().setTitle("Lyrics");
            selectedFragment = new SongsFragment();
        } else if (id == R.id.nav_videos) {
            getSupportActionBar().setTitle("Videos");
            selectedFragment = new VideosFragment();
        } else if (id == R.id.nav_photos) {
            getSupportActionBar().setTitle("Concert Photos");
            selectedFragment = new ConcertphotosFragment();
        } else if (id == R.id.nav_facebook) {
            getSupportActionBar().setTitle("Facebook");
            selectedFragment = new FacebookFragment();
        } else if (id == R.id.nav_google) {
            getSupportActionBar().setTitle("Google");
            selectedFragment = new GoogleFragment();
        } else if (id == R.id.nav_favourite) {
            getSupportActionBar().setTitle("Favourite");

            selectedFragment = new FavouriteFragment();
        } else if (id == R.id.nav_aboutus) {
            getSupportActionBar().setTitle("About Us");
            selectedFragment = new AboutusFragment();
        } else if (id == R.id.nav_rateus) {
            getSupportActionBar().setTitle("Rate Us");

            selectedFragment = new RateusFragment();
        } else if (id == R.id.nav_recording) {
            getSupportActionBar().setTitle("Recordings");
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/Lyrics/Records/");

            if (!file.exists()) {
                file.mkdirs();
            }
            selectedFragment = new RecordingFragment();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, selectedFragment);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
