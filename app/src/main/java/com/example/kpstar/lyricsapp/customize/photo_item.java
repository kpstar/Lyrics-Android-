package com.example.kpstar.lyricsapp.customize;

import android.database.Cursor;

import java.util.ArrayList;

public class photo_item {
    public int ID;
    public String Category;
    public String Title;
    public String Photo;

    public photo_item() {}

    public photo_item(String title, String photo) {
        Title = title;
        Photo = photo;
    }

    public photo_item(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String category = cursor.getString(cursor.getColumnIndex("category"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String photo = cursor.getString(cursor.getColumnIndex("photo"));

        this.ID = id;
        this.Category = category;
        this.Title = title;
        this.Photo = photo;
    }

    public String getTitle() {
        return Title;
    }

    public String getPhoto() {
        return Photo;
    }
}
