package com.example.kpstar.lyricsapp.songs;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kpstar.lyricsapp.MainActivity;
import com.example.kpstar.lyricsapp.R;
import com.example.kpstar.lyricsapp.customize.WSDatabase;
import com.example.kpstar.lyricsapp.customize.song_item;
import com.example.kpstar.lyricsapp.customize.songitem_adapter;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class RockFragment extends Fragment implements songitem_adapter.RecyclerViewClickListener{


    WSDatabase database;
    ArrayList<song_item> songItems;
    public SearchView searchView;
    songitem_adapter adapter;

    public RockFragment() {
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
        songItems = database.getSongItemsFromDatabase(0);
        adapter = new songitem_adapter(this.getActivity(), songItems, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);

        //searchView = (MainActivity)(getActivity()).getSearchView();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem item = menu.findItem(R.id.action_search);

        searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Intent mIntent = new Intent(this.getActivity(), PlayActivity.class);

        songItems = null;
        songItems = (ArrayList<song_item>) adapter.getmFilteritems();
        mIntent.putExtra("ID", songItems.get(position).getID());
        getActivity().startActivity(mIntent);
    }
}
