package com.example.kpstar.lyricsapp.customize;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.kpstar.lyricsapp.R;
import com.example.kpstar.lyricsapp.photos.RockFragment;

import java.util.ArrayList;
import java.util.List;

public class rockphoto_adapter extends RecyclerView.Adapter<rockphoto_adapter.MyViewHolder> implements Filterable{

    private static RecyclerViewClickListener itemListener;

    private List<photo_item> mSongitems;
    private List<photo_item> mFilteritems;
    private Context mContext;

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Pass in the contact array into the constructor
    public rockphoto_adapter(Context context, List<photo_item> song_items) {
        mSongitems = song_items;
        mFilteritems = song_items;
        mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleTxt);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }

    public rockphoto_adapter(Context context, List<photo_item> song_items, RockFragment itemListener) {
        this.mContext = context;

        mSongitems = song_items;
        mFilteritems = song_items;
        this.itemListener = itemListener;
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
                    List<photo_item> filteredList = new ArrayList<>();
                    for (photo_item row : mSongitems) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
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
                mFilteritems = (ArrayList<photo_item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        photo_item song_item = mFilteritems.get(position);

        TextView titletextView = viewHolder.title;
        titletextView.setText(song_item.getTitle());
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

    public void restoreItem(photo_item item, int position) {
        mFilteritems.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
