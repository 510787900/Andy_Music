package com.andy.music.activity;

/**
 * 播放状态的监听
 * Created by Andy on 2017/7/3.
 */

public interface MusicStateListener {

    /** 更新歌曲状态信息 */
    void updateTrackInfo();

    void updateTime();

    /** 更换主题 */
    void changeTheme();

    void reloadAdapter();
}
