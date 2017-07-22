package com.andy.music.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.music.MainApplication;
import com.andy.music.R;
import com.andy.music.adapter.MainFragmentAdapter;
import com.andy.music.item.MainFragmentItem;
import com.andy.music.info.Playlist;
import com.andy.music.provider.DownFileStore;
import com.andy.music.provider.PlaylistInfo;
import com.andy.music.utils.CommonUtils;
import com.andy.music.utils.IConstants;
import com.andy.music.utils.MusicUtils;
import com.andy.music.widgit.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地fragment
 * MainActivity 的第二页.中间位置.
 * 分为本地音乐等(4个)、创建的歌单、喜欢的音乐、收藏的歌单
 * Created by Andy on 2017/7/3.
 */

public class MainFragment extends BaseFragment {
    /** 下拉刷新layout */
    private SwipeRefreshLayout swipeRefresh;
    /** 可回收使用列表 */
    private RecyclerView recyclerView;
    //先在app 的build.gradle 的dependencies 中添加依赖：
    //compile 'com.android.support:recyclerview-v7:23.+'(我的编译版本为23)
    /** 列表的适配器 */
    private MainFragmentAdapter mAdapter;
    /** 本地音乐等4个列表的集合 */
    private List<MainFragmentItem> mList = new ArrayList<>();
    /** playlist 管理类 */
    private PlaylistInfo playlistInfo;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initView(rootView);

        reloadAdapter();//刷新列表
        return rootView;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO 初始化 playlist 管理类
        playlistInfo = PlaylistInfo.getInstance(mContext);
        if (CommonUtils.isLollipop() && ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            reloadAdapter();//刷新列表
        }
    }

    private void initView(View rootView) {
        swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        //设置recyclerView 的布局管理器
        /*layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);*/
        //设置recyclerView 的布局管理器.Manager中捕获一个异常
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));

        //先给adapter设置空数据，异步加载好后更新数据，防止Recyclerview no attach
        mAdapter = new MainFragmentAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
        //设置分割线
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL_LIST));
        //设置没有item动画
        recyclerView.getItemAnimator().setSupportsChangeAnimations(false);
        swipeRefresh.setColorSchemeResources(R.color.theme_color_red);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadAdapter();//下拉刷新.刷新列表
            }
        });
    }

    /** 刷新列表 */
    public void reloadAdapter() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                ArrayList results = new ArrayList();
                setMusicInfo();
                //TODO 获取创建和收藏的歌单信息
                ArrayList<Playlist> playlists = playlistInfo.getPlaylist();
                ArrayList<Playlist> netPlaylists = playlistInfo.getNetPlaylist();
                /*ArrayList<Playlist> playlists = new ArrayList<Playlist>();
                playlists.add(0,new Playlist(1, "我喜欢的音乐", 0, null, ""));
                ArrayList<Playlist> netPlaylists = new ArrayList<Playlist>();
                netPlaylists.add(0,new Playlist(1, "不如吃茶去", 17, null, "许嵩"));*/

                results.addAll(mList);
                results.add(mContext.getResources().getString(R.string.created_playlists));
                if (playlists != null && playlists.size() > 0) {
                    results.addAll(playlists);
                }
                results.add(mContext.getResources().getString(R.string.collect_playlists));
                if (netPlaylists != null && netPlaylists.size() > 0) {
                    results.addAll(netPlaylists);
                }

                if(mAdapter == null){
                    mAdapter = new MainFragmentAdapter(mContext);
                }
                mAdapter.updateResults(results, playlists, netPlaylists);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (mContext == null)
                    return;
                mAdapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        }.execute();
    }

    /** 设置本地音乐等四个列表条目 */
    private void setMusicInfo() {
        if (CommonUtils.isLollipop() && ContextCompat
                .checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            loadCount(false);
        } else {
            loadCount(true);
        }
    }

    /**
     * 加载前四个列表内容
     * @param has   是否有文件读取权限
     */
    private void loadCount(boolean has) {
        int localMusicCount = 0, recentMusicCount = 0,downLoadCount = 0 ,artistsCount = 0;
        //TODO 获取不同类型列表中的歌曲数量.
        if(has){
            try{
                localMusicCount = MusicUtils.queryMusic(mContext,
                        IConstants.START_FROM_LOCAL).size();
                //TODO 最近播放歌曲
                /*recentMusicCount = TopTracksLoader.getCount(MainApplication.context,
                        TopTracksLoader.QueryType.RecentSongs);*/
                downLoadCount = DownFileStore.getInstance(mContext)
                        .getDownLoadedListAll().size();
                artistsCount = MusicUtils.queryArtist(mContext).size();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        setInfo(mContext.getResources().getString(R.string.local_music),
                localMusicCount, R.drawable.music_icn_local, 0);
        setInfo(mContext.getResources().getString(R.string.recent_play),
                recentMusicCount, R.drawable.music_icn_recent, 1);
        setInfo(mContext.getResources().getString(R.string.local_manage),
                downLoadCount, R.drawable.music_icn_dld, 2);
        setInfo(mContext.getResources().getString(R.string.my_artist),
                artistsCount, R.drawable.music_icn_artist, 3);
    }

    /**
     * 为info设置数据，并放入mlistInfo
     * @param title     列表标题
     * @param count     列表类型的歌曲数量
     * @param id        图片ID
     * @param i         列表集合的位置
     */
    private void setInfo(String title, int count, int id, int i) {
        MainFragmentItem information = new MainFragmentItem();
        information.title = title;
        information.count = count;
        information.avatar = id;
        if (mList.size() < 4) {
            mList.add(new MainFragmentItem());
        }
        mList.set(i, information); //将新的info对象加入到信息列表中
    }

    /** 更换主题 */
    @Override
    public void changeTheme() {
        super.changeTheme();
        swipeRefresh.setColorSchemeResources(R.color.theme_color_red);
    }
}
