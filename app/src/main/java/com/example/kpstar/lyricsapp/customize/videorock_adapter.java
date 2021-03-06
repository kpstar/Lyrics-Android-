package com.example.kpstar.lyricsapp.customize;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.kpstar.lyricsapp.videos.RockFragment;
import com.google.api.services.youtube.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KpStar on 4/12/2018.
 */

public class videorock_adapter extends RecyclerView.Adapter<videorock_adapter.MyViewHolder> implements Filterable{

    private static RecyclerViewClickListener itemListener;

    private List<video_item> mSongitems;
    private List<video_item> mFilteritems;
    private Context mContext;

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Pass in the contact array into the constructor
    public videorock_adapter(Context context, List<video_item> song_items) {
        mSongitems = song_items;
        mFilteritems = song_items;
        mContext = context;
    }

    public List<video_item> getmFilteritems() {
        return mFilteritems;
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteritems = mSongitems;
                } else {
                    List<video_item> filteredList = new ArrayList<>();
                    for (video_item row : mSongitems) {

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
                mFilteritems = (ArrayList<video_item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public videorock_adapter(Context context, List<video_item> song_items, RockFragment itemListener) {
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
        video_item song_item = mFilteritems.get(position);

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

    public void restoreItem(video_item item, int position) {
        mFilteritems.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
