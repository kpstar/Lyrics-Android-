package com.example.kpstar.lyricsapp.customize;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kpstar.lyricsapp.R;
import com.example.kpstar.lyricsapp.fragment.RecordingFragment;
import com.example.kpstar.lyricsapp.songs.ReggaeFragment;

import java.util.List;

/**
 * Created by KpStar on 4/12/2018.
 */

public class record_adapter extends RecyclerView.Adapter<record_adapter.MyViewHolder> {

    private static RecyclerViewClickListener itemListener;

    private List<String> mSongitems;
    private Context mContext;

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Pass in the contact array into the constructor
    public record_adapter(Context context, List<String> song_items) {
        mSongitems = song_items;
        mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, bandname;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleTxt);
            bandname = view.findViewById(R.id.bandnameTxt);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }

    public record_adapter(Context context, List<String> song_items, RecordingFragment itemListener) {
        this.mContext = context;

        mSongitems = song_items;

        this.itemListener = itemListener;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        String song_item = mSongitems.get(position);

        TextView titletextView = viewHolder.title;
        titletextView.setText(song_item);
        TextView msgtextView = viewHolder.bandname;
        msgtextView.setText("Band Name");
    }

    @Override
    public int getItemCount() {
        return mSongitems.size();
    }



    public interface RecyclerViewClickListener
    {
        public void recyclerViewListClicked(View v, int position);
    }

    public void removeItem(int position) {
        mSongitems.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(String item, int position) {
        mSongitems.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
