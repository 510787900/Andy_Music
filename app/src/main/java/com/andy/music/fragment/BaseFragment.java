package com.andy.music.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.andy.music.activity.BaseActivity;
import com.andy.music.activity.MusicStateListener;

/**
 * 基础fragment
 * Created by Andy on 2017/7/3.
 */

public class BaseFragment extends Fragment implements MusicStateListener {
    public Activity mContext;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        ((BaseActivity) getActivity()).setMusicStateListenerListener(this);
        reloadAdapter();
    }

    @Override
    public void onStop() {
        super.onStop();
//        ((BaseActivity) getActivity()).removeMusicStateListenerListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void updateTrackInfo() {

    }

    @Override
    public void updateTime() {

    }

    /** 更换主题 */
    @Override
    public void changeTheme() {
    }

    /** 刷新列表 */
    @Override
    public void reloadAdapter() {

    }
}
