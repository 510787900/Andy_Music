package com.andy.music.utils;

/**
 * 常量接口
 * Created by Andy on 2017/7/6.
 */

public interface IConstants {

    //歌手和专辑列表点击都会进入MyMusic 此时要传递参数表明是从哪里进入的
    /** 艺术家 */
    int START_FROM_ARTIST = 1;
    /** 音乐专辑 */
    int START_FROM_ALBUM = 2;
    /** 本地音乐 */
    int START_FROM_LOCAL = 3;
    /** 文件夹 */
    int START_FROM_FOLDER = 4;
    /** 我喜欢的音乐 */
    int START_FROM_FAVORITE = 5;

    long FAV_PLAYLIST = 10L;

}
