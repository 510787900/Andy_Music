package com.andy.music.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.music.info.Playlist;

import java.util.ArrayList;

/**
 * 本地播放列表数据表
 * Created by Andy on 2017/7/6.
 */

public class PlaylistInfo {
    private MusicDB musicDatabase = null;

    public PlaylistInfo(Context context) {
        musicDatabase = MusicDB.getInstance(context);
    }

    // 单例模式
    private static PlaylistInfo instance = null;
    public static final synchronized PlaylistInfo getInstance(final Context context){
        if (instance == null)
            instance = new PlaylistInfo(context.getApplicationContext());
        return instance;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PlaylistInfoColumns.PLAYLIST_TABLE_NAME + "("
                + PlaylistInfoColumns.PLAYLIST_ID + " LONG NOT NULL,"
                + PlaylistInfoColumns.PLAYLIST_NAME + " CHAR NOT NULL,"
                + PlaylistInfoColumns.SONG_COUNT + " INT NOT NULL,"
                + PlaylistInfoColumns.ALBUM_ART + " CHAR,"
                + PlaylistInfoColumns.AUTHOR + " CHAR );"
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /** 清空表 */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PlaylistInfoColumns.PLAYLIST_TABLE_NAME);
        onCreate(db);
    }

    public synchronized ArrayList<Playlist> getPlaylist() {
        ArrayList<Playlist> results = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = musicDatabase.getReadableDatabase().query(
                    PlaylistInfoColumns.PLAYLIST_TABLE_NAME,
                    null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                results.ensureCapacity(cursor.getCount());

                do {
                    if (cursor.getString(4).equals("local"))
                        results.add(new Playlist(
                                cursor.getLong(0),
                                cursor.getString(1),
                                cursor.getInt(2),
                                cursor.getString(3),
                                cursor.getString(4)));
                } while (cursor.moveToNext());
            }

            return results;
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
    }

    public synchronized ArrayList<Playlist> getNetPlaylist() {
        ArrayList<Playlist> results = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = musicDatabase.getReadableDatabase().query(
                    PlaylistInfoColumns.PLAYLIST_TABLE_NAME,
                    null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                results.ensureCapacity(cursor.getCount());

                do {
                    if (!cursor.getString(4).equals("local"))
                        results.add(new Playlist(
                                cursor.getLong(0),
                                cursor.getString(1),
                                cursor.getInt(2),
                                cursor.getString(3),
                                cursor.getString(4)));
                } while (cursor.moveToNext());
            }

            return results;
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
    }

    public interface PlaylistInfoColumns {
        /** Table name */
        String PLAYLIST_TABLE_NAME = "playlist_info";

        /** Album IDs column */
        String PLAYLIST_ID = "playlist_id";

        /** Time played column */
        String PLAYLIST_NAME = "playlist_name";

        String SONG_COUNT = "count";

        String ALBUM_ART = "album_art";

        String AUTHOR = "author";
    }
}
