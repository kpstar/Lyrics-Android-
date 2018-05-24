package com.example.kpstar.lyricsapp.fragment;


import android.content.Intent;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kpstar.lyricsapp.R;
import com.example.kpstar.lyricsapp.customize.RecyclerItemTouchHelper;
import com.example.kpstar.lyricsapp.customize.WSDatabase;
import com.example.kpstar.lyricsapp.customize.record_adapter;
import com.example.kpstar.lyricsapp.customize.reggaeitem_adapter;
import com.example.kpstar.lyricsapp.customize.song_item;
import com.example.kpstar.lyricsapp.songs.PlayActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordingFragment extends Fragment implements record_adapter.RecyclerViewClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {


    WSDatabase database;
    ArrayList<String> songItems;
    MediaPlayer mediaPlayer;
    private record_adapter adapter = null;
    RecyclerView recyclerView = null;

    public RecordingFragment() {
        // Required empty public constructor
    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reggae, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);



//        database = new WSDatabase(getContext());
//        database.open();

        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Lyrics/Records");

        if (!file.exists()) {
            try {
                if (file.createNewFile()) return view;
                else
                    Toast.makeText(getContext(), "Error Reading File", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getContext(), "No Record File Exists", Toast.LENGTH_SHORT).show();
                return view;
            }
        }
        File list[] = file.listFiles();

        songItems = new ArrayList<String>();
        for( int i=0; i< list.length; i++)
        {
            songItems.add( list[i].getName() );
        }


        setHasOptionsMenu(true);
        //songItems = database.getSongItemsFromDatabase(1);
        adapter = new record_adapter(this.getActivity(), songItems, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
//        Intent mIntent = new Intent(this.getActivity(), PlayActivity.class);
//        mIntent.putExtra("ID", songItems.get(position));
//        getActivity().startActivityForResult(mIntent, 200);

        if ( mediaPlayer!= null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/Lyrics/Records/"+songItems.get(position));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        String item = songItems.get(viewHolder.getAdapterPosition());
        if ( mediaPlayer!= null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }



        File file = new File(Environment.getExternalStorageDirectory()+"/Lyrics/Records/"+songItems.get(position));
        if (file.exists()) {
            file.delete();
        }

        songItems.remove(viewHolder.getAdapterPosition());

        adapter = new record_adapter(this.getActivity(), songItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }
}
