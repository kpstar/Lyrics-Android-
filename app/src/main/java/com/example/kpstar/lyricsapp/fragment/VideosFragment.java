package com.example.kpstar.lyricsapp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kpstar.lyricsapp.R;
import com.example.kpstar.lyricsapp.videos.ReggaeFragment;
import com.example.kpstar.lyricsapp.videos.RockFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment {

    private InterstitialAd interstitialAd;

    public VideosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        MobileAds.initialize(getContext(), getString(R.string.admob_app_id));
        AdRequest adBannerRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adBannerRequest);
        mAdView.setAdListener(new AdListener() { });

        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId("ca-app-pub-5557706460832127/1744200120");
        interstitialAd.loadAd(adBannerRequest);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        AdRequest adBannerRequest = new AdRequest.Builder().build();
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId("ca-app-pub-5557706460832127/1744200120");
        interstitialAd.loadAd(adBannerRequest);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Video", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("Interstitial", false)) {

            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                }
            });
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("Interstitial", false);
            editor.apply();
        }
    }

    private void setupViewPager(ViewPager viewPager) {


        SongsFragment.Adapter adapter = new SongsFragment.Adapter(getChildFragmentManager());
        adapter.addFragment(new RockFragment(), "ROCK");
        adapter.addFragment(new ReggaeFragment(), "REGGAE");
        viewPager.setAdapter(adapter);

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
