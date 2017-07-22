package com.andy.music.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 本地音乐管理数据库
 * Created by Andy on 2017/7/6.
 */

public class MusicDB extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "andy_music_db.db";
    private static final int VERSION = 2;
    private final Context context;
    //单例
    private static MusicDB instance = null;
    public static final synchronized MusicDB getInstance (final Context context) {
        if (null == instance)
            instance = new MusicDB(context.getApplicationContext());
        return instance;
    }


    public MusicDB(final Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DownFileStore.getInstance(context).onCreate(db);
        MusicPlaybackState.getInstance(context).onCreate(db);
        PlaylistInfo.getInstance(context).onCreate(db);
        PlaylistsManager.getInstance(context).onCreate(db);
        RecentStore.getInstance(context).onCreate(db);
        SearchHistory.getInstance(context).onCreate(db);
        SongPlayCount.getInstance(context).onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DownFileStore.getInstance(context).onUpgrade(db, oldVersion, newVersion);
        MusicPlaybackState.getInstance(context).onUpgrade(db, oldVersion, newVersion);
        PlaylistInfo.getInstance(context).onUpgrade(db, oldVersion, newVersion);
        PlaylistsManager.getInstance(context).onUpgrade(db, oldVersion, newVersion);
        RecentStore.getInstance(context).onUpgrade(db, oldVersion, newVersion);
        SearchHistory.getInstance(context).onUpgrade(db, oldVersion, newVersion);
        SongPlayCount.getInstance(context).onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DownFileStore.getInstance(context).onDowngrade(db, oldVersion, newVersion);
        MusicPlaybackState.getInstance(context).onDowngrade(db, oldVersion, newVersion);
        PlaylistInfo.getInstance(context).onDowngrade(db, oldVersion, newVersion);
        PlaylistsManager.getInstance(context).onDowngrade(db, oldVersion, newVersion);
        RecentStore.getInstance(context).onDowngrade(db, oldVersion, newVersion);
        SearchHistory.getInstance(context).onDowngrade(db, oldVersion, newVersion);
        SongPlayCount.getInstance(context).onDowngrade(db, oldVersion, newVersion);
    }
}
