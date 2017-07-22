package com.andy.music.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.andy.music.utils.IConstants;

/**
 * 当前的播放列表
 * Created by Andy on 2017/7/6.
 */

public class PlaylistsManager {
    private static PlaylistsManager instance = null;
    public static final synchronized PlaylistsManager getInstance(final Context context) {
        if (instance == null) {
            instance = new PlaylistsManager(context.getApplicationContext());
        }
        return instance;
    }

    private MusicDB musicDatabase = null;
    private long favPlaylistId = IConstants.FAV_PLAYLIST;

    public PlaylistsManager(final Context context) {
        musicDatabase = MusicDB.getInstance(context);
    }


    public void onCreate(SQLiteDatabase db) {
        //建立播放列表表设置播放列表id和歌曲id为复合主键
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PlaylistsColumns.NAME + " ("
                + PlaylistsColumns.PLAYLIST_ID + " LONG NOT NULL,"
                + PlaylistsColumns.TRACK_ID + " LONG NOT NULL,"
                + PlaylistsColumns.TRACK_NAME + " CHAR NOT NULL,"
                + PlaylistsColumns.ALBUM_ID + " LONG,"
                + PlaylistsColumns.ALBUM_NAME + " CHAR,"
                + PlaylistsColumns.ALBUM_ART + " CHAR,"
                + PlaylistsColumns.ARTIST_ID + " LONG,"
                + PlaylistsColumns.ARTIST_NAME + " CHAR,"
                + PlaylistsColumns.IS_LOCAL + " BOOLEAN ,"
                + PlaylistsColumns.PATH + " CHAR,"
                + PlaylistsColumns.TRACK_ORDER + " LONG NOT NULL, primary key ( "
                + PlaylistsColumns.PLAYLIST_ID + ", "
                + PlaylistsColumns.TRACK_ID + "));");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PlaylistsColumns.NAME);
        onCreate(db);
    }

    public interface PlaylistsColumns {
        /* Table name */
        String NAME = "playlists";

        /* Album IDs column */
        String PLAYLIST_ID = "playlist_id";

        /* Time played column */
        String TRACK_ID = "track_id";

        String TRACK_ORDER = "track_order";

        String TRACK_NAME = "track_name";

        String ARTIST_NAME = "artist_name";

        String ARTIST_ID = "artist_id";

        String ALBUM_NAME = "album_name";

        String ALBUM_ID = "album_id";

        String IS_LOCAL = "is_local";

        String PATH = "path";

        String ALBUM_ART = "album_art";
    }
}
