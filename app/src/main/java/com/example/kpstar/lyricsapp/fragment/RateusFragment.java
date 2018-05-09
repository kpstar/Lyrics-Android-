package com.example.kpstar.lyricsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.kpstar.lyricsapp.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateusFragment extends Fragment {

    public AdView mAdView;

    public RateusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_google, container, false);
        MobileAds.initialize(getContext(), getString(R.string.admob_app_id));
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adBannerRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adBannerRequest);
        mAdView.setAdListener(new AdListener() { });

        String url = "https://play.google.com/store/apps/details?id=com.avrea.chords";

        WebView webView = (WebView)view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        return view;
    }

}
