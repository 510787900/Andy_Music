package com.andy.music.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

/**
 *
 * Created by Andy on 2017/7/6.
 */

public class PreferencesUtility {
    public static final String SONG_SORT_ORDER = "song_sort_order";
    public static final String ARTIST_SONG_SORT_ORDER = "artist_song_sort_order";
    public static final String ALBUM_SONG_SORT_ORDER = "album_song_sort_order";
    public static final String ARTIST_SORT_ORDER = "artist_sort_order";

    private static PreferencesUtility instance;
    public static final PreferencesUtility getInstance(final Context context) {
        if (instance == null) {
            instance = new PreferencesUtility(context.getApplicationContext());
        }
        return instance;
    }

    private static SharedPreferences mPreferences;
    public PreferencesUtility(final Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /** 歌曲A-Z排序 */
    public final String getSongSortOrder() {
        return mPreferences.getString(SONG_SORT_ORDER,
                SortOrder.SongSortOrder.SONG_A_Z);
    }

    /** 艺术家歌曲A-Z排序 */
    public final String getArtistSongSortOrder() {
        return mPreferences.getString(ARTIST_SONG_SORT_ORDER,
                SortOrder.ArtistSongSortOrder.SONG_A_Z);
    }

    /** 歌曲专辑排序 */
    public final String getAlbumSongSortOrder() {
        return mPreferences.getString(ALBUM_SONG_SORT_ORDER,
                SortOrder.AlbumSongSortOrder.SONG_TRACK_LIST);
    }

    /** 艺术家A-Z排序 */
    public final String getArtistSortOrder() {
        return mPreferences.getString(ARTIST_SORT_ORDER,
                SortOrder.ArtistSortOrder.ARTIST_A_Z);
    }

    /**
     * 歌曲排序
     * @param value     排序方式.如按字母排序
     */
    public void setSongSortOrder(final String value) {
        setSortOrder(SONG_SORT_ORDER, value);
    }

    /**
     * 排序
     * @param key       排序内容.如歌曲排序/专辑排序
     * @param value     排序方式.如按字母排序
     */
    private void setSortOrder(final String key, final String value) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                final SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString(key, value);
                editor.apply();
                return null;
            }
        }.execute();
    }
}
