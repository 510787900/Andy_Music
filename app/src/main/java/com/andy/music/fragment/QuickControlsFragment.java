package com.andy.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andy.music.R;
import com.andy.music.utils.LogUtils;

/**
 * 底部音乐状态控制的Fragment
 * Created by Andy on 2017/7/4.
 */

public class QuickControlsFragment extends BaseFragment{
    private static QuickControlsFragment instance = new QuickControlsFragment();
    public static QuickControlsFragment newInstance(){
        return instance;
    }

    private ProgressBar progressBar;
    private TextView title, singer;
    private ImageView img_cover, img_playlist, img_play_pause, img_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quick_controls, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        progressBar = (ProgressBar)rootView.findViewById(R.id.playbar_progressBar);
        title = (TextView)rootView.findViewById(R.id.playbar_title);
        singer = (TextView)rootView.findViewById(R.id.playbar_singer);
        img_cover = (ImageView)rootView.findViewById(R.id.playbar_img_cover);
        img_playlist = (ImageView)rootView.findViewById(R.id.play_img_playlist);
        img_play_pause = (ImageView)rootView.findViewById(R.id.play_img_control);
        img_next = (ImageView)rootView.findViewById(R.id.play_img_next);
        img_playlist.setOnClickListener(listener);
        img_play_pause.setOnClickListener(listener);
        img_next.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.play_img_playlist://播放列表
                    LogUtils.toast(getContext(),"播放列表");
                    break;
                case R.id.play_img_control://播放暂停
                    LogUtils.toast(getContext(),"播放暂停");
                    break;
                case R.id.play_img_next://下一首
                    LogUtils.toast(getContext(),"下一首");
                    break;
                default:
                    break;
            }
        }
    };
}
