package com.andy.music.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Andy on 2017/7/6.
 */

public class MusicPlaybackState {
    private static MusicPlaybackState instance = null;
    public static final synchronized MusicPlaybackState getInstance(final Context context) {
        if (instance == null) {
            instance = new MusicPlaybackState(context.getApplicationContext());
        }
        return instance;
    }

    private MusicDB musicDatabase = null;

    public MusicPlaybackState(final Context context) {
        musicDatabase = MusicDB.getInstance(context);
    }


    public void onCreate(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(PlaybackQueueColumns.NAME);
        builder.append("(");

        builder.append(PlaybackQueueColumns.TRACK_ID);
        builder.append(" LONG NOT NULL,");

        builder.append(PlaybackQueueColumns.SOURCE_POSITION);
        builder.append(" INT NOT NULL);");

        db.execSQL(builder.toString());

        builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(PlaybackHistoryColumns.NAME);
        builder.append("(");

        builder.append(PlaybackHistoryColumns.POSITION);
        builder.append(" INT NOT NULL);");

        db.execSQL(builder.toString());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this table was created in version 2 so call the onCreate method if we hit that scenario
        if (oldVersion < 2 && newVersion >= 2) {
            onCreate(db);
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PlaybackQueueColumns.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PlaybackHistoryColumns.NAME);
        onCreate(db);
    }

    public class PlaybackQueueColumns {
        public static final String NAME = "playbacklist";
        public static final String TRACK_ID = "trackid";
        public static final String SOURCE_POSITION = "sourceposition";
    }

    public class PlaybackHistoryColumns {
        public static final String NAME = "playbackhistory";
        public static final String POSITION = "position";
    }
}
