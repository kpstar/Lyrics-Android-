package com.example.kpstar.lyricsapp.customize;

import android.database.Cursor;

import java.util.ArrayList;

public class video_item {
    public int ID;
    public String Category;
    public String Title;
    public String Band;
    public String Url;

    public video_item() {}

    public video_item(String title, String band) {
        Title = title;
        Band = band;
    }


    public video_item(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String category = cursor.getString(cursor.getColumnIndex("category"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String band = cursor.getString(cursor.getColumnIndex("band"));
        String url = cursor.getString(cursor.getColumnIndex("url"));

        this.ID = id;
        this.Category = category;
        this.Title = title;
        this.Band = band;
        this.Url = url;
    }

    public String getTitle() {
        return Title;
    }

    public String getBand() { return Band; }

    public String getUrl() { return Url; }


    public int getID () { return ID; }

}
