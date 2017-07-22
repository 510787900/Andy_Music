package com.andy.music.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 最近播放列表
 * Created by Andy on 2017/7/6.
 */

public class RecentStore {
    private static RecentStore instance = null;
    public static final synchronized RecentStore getInstance(final Context context) {
        if (instance == null) {
            instance = new RecentStore(context.getApplicationContext());
        }
        return instance;
    }

    /** 最近播放列表最多显示个数 */
    private static final int MAX_ITEMS_IN_DB = 100;
    private MusicDB musicDatabase = null;

    public RecentStore(final Context context) {
        musicDatabase = MusicDB.getInstance(context);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + RecentStoreColumns.NAME + " ("
                + RecentStoreColumns.ID + " LONG NOT NULL,"
                + RecentStoreColumns.TIMEPLAYED + " LONG NOT NULL);"
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RecentStoreColumns.NAME);
        onCreate(db);
    }

    public interface RecentStoreColumns {
        /* Table name */
        String NAME = "recenthistory";

        /* Album IDs column */
        String ID = "songid";

        /* Time played column */
        String TIMEPLAYED = "timeplayed";
    }
}
