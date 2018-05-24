package com.example.kpstar.lyricsapp.photos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.kpstar.lyricsapp.R;
import com.example.kpstar.lyricsapp.customize.WSDatabase;
import com.example.kpstar.lyricsapp.customize.photo_item;
import com.example.kpstar.lyricsapp.customize.rockphoto_adapter;
import com.example.kpstar.lyricsapp.customize.song_item;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RockFragment extends Fragment implements rockphoto_adapter.RecyclerViewClickListener{


    WSDatabase database;
    ArrayList<photo_item> photoItems;
    rockphoto_adapter adapter;
    private SearchView searchView;

    public RockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        database = new WSDatabase(getContext());
        database.open();
        photoItems = database.getPhotoItemsFromDatabase(0);
        adapter = new rockphoto_adapter(this.getActivity(), photoItems, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);

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
        Intent mIntent = new Intent(this.getActivity(), PhotoActivity.class);
        photoItems = null;
        photoItems = (ArrayList<photo_item>) adapter.getmFilteritems();
        mIntent.putExtra("PHOTONAME", photoItems.get(position).getTitle());
        mIntent.putExtra("PHOTOS", photoItems.get(position).getPhoto());
        getActivity().startActivity(mIntent);
    }
}
