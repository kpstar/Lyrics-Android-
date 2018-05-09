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
public class FacebookFragment extends Fragment {


    public FacebookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_google, container, false);

        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        MobileAds.initialize(getContext(), getString(R.string.admob_app_id));
        AdRequest adBannerRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adBannerRequest);
        mAdView.setAdListener(new AdListener() { });

        String url = "https://www.facebook.com/opmchord";
//        String url = "https://dealperson.herokuapp.com";

        WebView webView = (WebView)view.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        return view;
    }
}
