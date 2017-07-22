package com.andy.music.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 搜索历史记录列表
 * Created by Andy on 2017/7/6.
 */

public class SearchHistory {
    private static SearchHistory instance = null;
    public static final synchronized SearchHistory getInstance(final Context context) {
        if (instance == null) {
            instance = new SearchHistory(context.getApplicationContext());
        }
        return instance;
    }

    private MusicDB musicDatabase = null;
    /** 最多显示搜索历史记录的个数 */
    private static final int MAX_ITEMS_IN_DB = 25;

    public SearchHistory(final Context context) {
        musicDatabase = MusicDB.getInstance(context);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + SearchHistoryColumns.NAME + " ("
                + SearchHistoryColumns.SEARCHSTRING + " TEXT NOT NULL,"
                + SearchHistoryColumns.TIMESEARCHED + " LONG NOT NULL);"
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SearchHistoryColumns.NAME);
        onCreate(db);
    }

    public interface SearchHistoryColumns {
        /* Table name */
        String NAME = "searchhistory";

        /* What was searched */
        String SEARCHSTRING = "searchstring";

        /* Time of search */
        String TIMESEARCHED = "timesearched";
    }
}
