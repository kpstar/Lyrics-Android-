package com.example.kpstar.lyricsapp.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kpstar.lyricsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutusFragment extends Fragment {


    public AboutusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aboutus, container, false);

        Button openWebBtn = (Button)view.findViewById(R.id.openWebsite);
        openWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.buryavrea.com";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

}
