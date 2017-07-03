package com.andy.music.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.andy.music.fragment.BaseFragment;

/**
 * Created by Andy on 2017/7/3.
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*mToken = MusicPlayer.bindToService(this, this);
        mPlaybackStatus = new PlaybackStatus(this);

        IntentFilter f = new IntentFilter();
        f.addAction(MediaService.PLAYSTATE_CHANGED);
        f.addAction(MediaService.META_CHANGED);
        f.addAction(MediaService.QUEUE_CHANGED);
        f.addAction(IConstants.MUSIC_COUNT_CHANGED);
        f.addAction(MediaService.TRACK_PREPARED);
        f.addAction(MediaService.BUFFER_UP);
        f.addAction(IConstants.EMPTY_LIST);
        f.addAction(MediaService.MUSIC_CHANGED);
        f.addAction(MediaService.LRC_UPDATED);
        f.addAction(IConstants.PLAYLIST_COUNT_CHANGED);
        f.addAction(MediaService.MUSIC_LODING);
        registerReceiver(mPlaybackStatus, new IntentFilter(f));
        showQuickControl(true);*/
    }

    public void setMusicStateListenerListener(BaseFragment baseFragment) {

    }

    public void removeMusicStateListenerListener(BaseFragment baseFragment) {

    }

}

/*public class BaseActivity extends AppCompatActivity implements ServiceConnection{
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }


}*/
