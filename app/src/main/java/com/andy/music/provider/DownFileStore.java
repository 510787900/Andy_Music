package com.andy.music.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.music.downmusic.DownloadDBEntity;

import java.util.ArrayList;

/**
 * 下载列表数据表
 * Created by Andy on 2017/7/6.
 */

public class DownFileStore {
    private static DownFileStore instance = null;
    public static synchronized DownFileStore getInstance(final Context context) {
        if (instance == null) {
            instance = new DownFileStore(context.getApplicationContext());
        }
        return instance;
    }

    private MusicDB musicDatabase = null;

    public DownFileStore(final Context context) {
        musicDatabase = MusicDB.getInstance(context);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DownFileStoreColumns.NAME + " ("
                + DownFileStoreColumns.ID + " TEXT NOT NULL PRIMARY KEY,"
                + DownFileStoreColumns.TOOL_SIZE + " INT NOT NULL,"
                + DownFileStoreColumns.FILE_LENGTH + " INT NOT NULL, "
                + DownFileStoreColumns.URL + " TEXT NOT NULL,"
                + DownFileStoreColumns.DIR + " TEXT NOT NULL,"
                + DownFileStoreColumns.FILE_NAME + " TEXT NOT NULL,"
                + DownFileStoreColumns.ARTIST_NAME + " TEXT NOT NULL,"
                + DownFileStoreColumns.DOWNSTATUS + " INT NOT NULL);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DownFileStoreColumns.NAME);
        onCreate(db);
    }

    /**
     * 查询下载的歌曲
     * @return
     */
    public synchronized ArrayList<DownloadDBEntity> getDownLoadedListAll() {
        ArrayList<DownloadDBEntity> results = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = musicDatabase.getReadableDatabase().query(DownFileStoreColumns.NAME, null,
                    null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                results.ensureCapacity(cursor.getCount());

                do {
                    results.add(new DownloadDBEntity(
                            cursor.getString(0),
                            cursor.getLong(1),
                            cursor.getLong(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7)
                    ));
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

    public interface DownFileStoreColumns {
        /** Table name */
        String NAME = "downfile_info";

        /** Album IDs column */
        String ID = "id";

        /** Time played column */
        String TOOL_SIZE = "totalsize";

        String FILE_LENGTH = "complete_length";

        String URL = "url";

        String DIR = "dir";
        String FILE_NAME = "file_name";
        String ARTIST_NAME = "artist";
        String DOWNSTATUS = "notification_type";
    }
}
