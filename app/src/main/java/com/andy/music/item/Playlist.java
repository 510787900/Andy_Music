package com.andy.music.item;

/**
 * 歌单列表
 * Created by Andy on 2017/7/4.
 */

public class Playlist {
    public final long id;
    public final String name;
    public final int songCount;
    public final String albumArt;
    public final String author;

    public Playlist(){
        this.id = -1;
        this.name = "";
        this.songCount = -1;
        this.albumArt = "";
        this.author = "";
    }

    public Playlist(long id, String name, int songCount, String albumArt, String author){
        this.id = id;
        this.name = name;
        this.songCount = songCount;
        this.albumArt = albumArt;
        this.author = author;
    }

}
