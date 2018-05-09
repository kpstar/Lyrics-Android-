package com.example.kpstar.lyricsapp.customize;

import android.database.Cursor;

import java.util.ArrayList;

public class song_item {
    public int ID;
    public String Category;
    public String Title;
    public String Band;
    public String Lyrics;
    public int Favourite;
    public String Audio;
    public String Photoname;

    public song_item() {}

    public song_item(String title, String band) {
        Title = title;
        Band = band;
    }

    public static ArrayList<song_item> createSongsList(int numMsgs) {
        ArrayList<song_item> songItems = new ArrayList<song_item>();

        for (int i=1; i<=numMsgs; i++) {
            songItems.add(new song_item("Title","Band name"));
        }
        return songItems;
    }

    public song_item(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String category = cursor.getString(cursor.getColumnIndex("category"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String audio = cursor.getString(cursor.getColumnIndex("name"));
        String band = cursor.getString(cursor.getColumnIndex("band"));
        String photoname = cursor.getString(cursor.getColumnIndex("photoname"));
        String content = cursor.getString(cursor.getColumnIndex("content"));
        int favourite = cursor.getInt(cursor.getColumnIndex("favourite"));

        this.Audio = audio;
        this.ID = id;
        this.Category = category;
        this.Title = title;
        this.Band = band;
        this.Lyrics = content;
        this.Favourite = favourite;
        this.Photoname = photoname;
    }

    public String getTitle() {
        return Title;
    }

    public String getBand() { return Band; }

    public String getSongurl() { return Audio; }

    public boolean getFavorite() {
        if (Favourite == 1) {
            return true;
        }
        return false;
    }

    public int getID () { return ID; }

    public String getPhotoname() { return Photoname; }
    public String getLyrics() { return Lyrics; }
}
