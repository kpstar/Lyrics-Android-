package com.example.kpstar.lyricsapp.customize;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kpstar.lyricsapp.R;
import com.example.kpstar.lyricsapp.songs.RockFragment;
import com.example.kpstar.lyricsapp.songs.ReggaeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KpStar on 4/12/2018.
 */

public class reggaeitem_adapter extends RecyclerView.Adapter<reggaeitem_adapter.MyViewHolder> implements Filterable {

    private static RecyclerViewClickListener itemListener;

    private List<song_item> mSongitems;
    private List<song_item> mFilteritems;
    private Context mContext;

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Pass in the contact array into the constructor
    public reggaeitem_adapter(Context context, List<song_item> song_items) {
        mSongitems = song_items;
        mFilteritems = song_items;
        mContext = context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteritems = mSongitems;
                } else {
                    List<song_item> filteredList = new ArrayList<>();
                    for (song_item row : mSongitems) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getBand().toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    mFilteritems = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteritems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteritems = (ArrayList<song_item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, bandname;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleTxt);
            bandname = view.findViewById(R.id.bandnameTxt);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }

    public reggaeitem_adapter(Context context, List<song_item> song_items, ReggaeFragment itemListener) {
        this.mContext = context;

        mSongitems = song_items;
        mFilteritems = song_items;
        this.itemListener = itemListener;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        song_item song_item = mFilteritems.get(position);

        TextView titletextView = viewHolder.title;
        titletextView.setText(song_item.getTitle());
        TextView msgtextView = viewHolder.bandname;
        msgtextView.setText(song_item.getBand());
    }

    @Override
    public int getItemCount() {
        return mFilteritems.size();
    }



    public interface RecyclerViewClickListener
    {
        public void recyclerViewListClicked(View v, int position);
    }

    public void removeItem(int position) {
        mFilteritems.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(song_item item, int position) {
        mFilteritems.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
