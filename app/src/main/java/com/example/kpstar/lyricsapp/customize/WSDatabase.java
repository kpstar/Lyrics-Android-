package com.example.kpstar.lyricsapp.customize;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WSDatabase extends SQLiteAssetHelper {


    private static final String DATABASE_NAME = "lyrics.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;
    public String lyrics_table = "songitems";
    public String lyrics_songid = "id";
    public String lyrics_category = "category";
    public String lyrics_favourite = "favourite";
    public String lyrics_video = "videoitems";
    public String lyrics_photo = "photoitems";
    public SQLiteDatabase database;


    public WSDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
        this.context = context;
    }


    public void open(){
        database = getReadableDatabase();
    }


    public song_item getSongItem(int id) {
        String getitem = String.format("select * from %s where %s='%d'", lyrics_table, lyrics_songid, id);
        Cursor cursor = database.rawQuery(getitem, null);
        song_item songItem = null;
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            songItem = new song_item(cursor);
        }
        cursor.close();
        return songItem;
    }

    public ArrayList<song_item> getSongItemsFromDatabase(int whatmusic) {
        String strGetsongs;
        if (whatmusic == 0) {
            strGetsongs = String.format("select * from %s where %s = 'Rock'", lyrics_table, lyrics_category);
        } else {
            strGetsongs = String.format("select * from %s where %s = 'Reggae'", lyrics_table, lyrics_category);
        }
        Cursor cursor = database.rawQuery(strGetsongs, null);
        ArrayList<song_item> songItems = new ArrayList<song_item>();
        while (cursor.moveToNext()){
            songItems.add(new song_item(cursor));
        }

        if (cursor!=null && !cursor.isClosed())
            cursor.close();
        return songItems;
    }

    public ArrayList<photo_item> getPhotoItemsFromDatabase(int whatmusic) {
        String strGetphotos;
        if (whatmusic == 0) {
            strGetphotos = String.format("select * from %s where %s = 'Rock'", lyrics_photo, lyrics_category);
        } else {
            strGetphotos = String.format("select * from %s where %s = 'Reggae'", lyrics_photo, lyrics_category);
        }
        Cursor cursor = database.rawQuery(strGetphotos, null);
        ArrayList<photo_item> photoItems = new ArrayList<photo_item>();
        while (cursor.moveToNext()){
            photoItems.add(new photo_item(cursor));
        }

        if (cursor!=null && !cursor.isClosed())
            cursor.close();
        return photoItems;
    }

    public ArrayList<video_item> getVideoItemsFromDatabase(int whatmusic) {
        String strGetsongs;
        if (whatmusic == 0) {
            strGetsongs = String.format("select * from %s where %s = 'Rock'", lyrics_video, lyrics_category);
        } else {
            strGetsongs = String.format("select * from %s where %s = 'Reggae'", lyrics_video, lyrics_category);
        }
        Cursor cursor = database.rawQuery(strGetsongs, null);
        ArrayList<video_item> videosItems = new ArrayList<video_item>();
        while (cursor.moveToNext()){
            videosItems.add(new video_item(cursor));
        }

        if (cursor!=null && !cursor.isClosed())
            cursor.close();
        return videosItems;
    }

    public ArrayList<song_item> getFavouriteSongItems() {
        String strGetsongs;
        strGetsongs = String.format("select * from %s where %s = '1'", lyrics_table, lyrics_favourite);
        Cursor cursor = database.rawQuery(strGetsongs, null);
        ArrayList<song_item> songItems = new ArrayList<song_item>();
        while (cursor.moveToNext()){
            songItems.add(new song_item(cursor));
        }

        if (cursor!=null && !cursor.isClosed())
            cursor.close();
        return songItems;
    }

    public void setFavourite(int id, int flag) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(lyrics_favourite, flag);
        database.update(lyrics_table, initialValues, String.format("%s='%d'", lyrics_songid, id), null);
    }
}

