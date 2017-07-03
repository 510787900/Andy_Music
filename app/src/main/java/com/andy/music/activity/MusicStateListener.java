package com.andy.music.activity;

/**
 * Created by Andy on 2017/7/3.
 */

public interface MusicStateListener {
    /**
     * 更新歌曲状态信息
     */
    void updateTrackInfo();

    void updateTime();

    void changeTheme();

    void reloadAdapter();
}
