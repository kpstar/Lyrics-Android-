package com.example.kpstar.lyricsapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kpstar.lyricsapp.R;
import com.example.kpstar.lyricsapp.customize.WSDatabase;
import com.example.kpstar.lyricsapp.customize.favourite_adapter;
import com.example.kpstar.lyricsapp.customize.song_item;
import com.example.kpstar.lyricsapp.customize.songitem_adapter;
import com.example.kpstar.lyricsapp.songs.PlayActivity;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment implements favourite_adapter.RecyclerViewClickListener {


    WSDatabase database;
    ArrayList<song_item> songItems;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rock, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        database = new WSDatabase(getContext());
        database.open();
        songItems = database.getFavouriteSongItems();
        favourite_adapter adapter = new favourite_adapter(this.getActivity(), songItems, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Intent mIntent = new Intent(this.getActivity(), PlayActivity.class);
        mIntent.putExtra("ID", songItems.get(position).getID());
        getActivity().startActivity(mIntent);
    }
}
