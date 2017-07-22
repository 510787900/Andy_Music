package com.andy.music.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.music.R;
import com.andy.music.info.MusicInfo;
import com.andy.music.utils.IConstants;
import com.andy.music.utils.LogUtils;
import com.andy.music.utils.MusicComparator;
import com.andy.music.utils.MusicUtils;
import com.andy.music.utils.PreferencesUtility;
import com.andy.music.utils.SortOrder;
import com.andy.music.widgit.DividerItemDecoration;
import com.andy.music.widgit.SideBar;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * 列表单曲fragment.
 * TabActivity 的第1页
 * Created by Andy on 2017/7/7.
 */

public class TabMusicFragment extends BaseFragment {
    private TabMusicAdapter adapter;
    private ArrayList<MusicInfo> musicInfos;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private PreferencesUtility mPreferences;
    private FrameLayout frameLayout;
    private View view;
    private boolean isFirstLoad = true;
    private SideBar sideBar;
    private TextView dialogText;
    private HashMap<String, Integer> positionMap = new HashMap<>();
    private boolean isAZSort = true;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferencesUtility.getInstance(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.load_framelayout, container, false);
        frameLayout = (FrameLayout) view.findViewById(R.id.loadframe);
        View loadView = LayoutInflater.from(mContext).inflate(R.layout.loading, frameLayout,false);
        frameLayout.addView(loadView);
        isFirstLoad = true;
        isAZSort = mPreferences.getSongSortOrder().equals(SortOrder.SongSortOrder.SONG_A_Z);

        if(getUserVisibleHint()){
            loadView();
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            loadView();
        }
    }

    private void loadView() {
        //setUservisibleHint 可能先与attach
        if (view == null && mContext != null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.recylerview, frameLayout, false);

            dialogText = (TextView) view.findViewById(R.id.dialog_text);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
            layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new TabMusicAdapter(null);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            //fastScroller = (FastScroller) view.findViewById(R.id.fastscroller);
            recyclerView.addItemDecoration(new DividerItemDecoration(mContext,
                    DividerItemDecoration.VERTICAL_LIST));

            sideBar = (SideBar) view.findViewById(R.id.sidebar);
            sideBar.setOnTouchingLetterChangedListener(new SideBar
                    .OnTouchingLetterChangedListener() {
                @Override
                public void onTouchingLetterChanged(String s) {
                    dialogText.setText(s);
                    sideBar.setView(dialogText);
                    if (positionMap.get(s) != null) {
                        int i = positionMap.get(s);
                        ((LinearLayoutManager) recyclerView.getLayoutManager())
                                .scrollToPositionWithOffset(i + 1, 0);
                    }

                }
            });
            reloadAdapter();
            LogUtils.showLog("load l");
        }
    }

    /** 刷新列表 */
    @Override
    public void reloadAdapter() {
        if (adapter == null)
            return;

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                isAZSort = mPreferences.getSongSortOrder().equals(SortOrder.SongSortOrder.SONG_A_Z);
                ArrayList<MusicInfo> songList = (ArrayList) MusicUtils
                        .queryMusic(mContext, IConstants.START_FROM_LOCAL);
                // 名称排序时，重新排序并加入位置信息
                if (isAZSort) {
                    Collections.sort(songList, new MusicComparator());
                    for (int i = 0; i < songList.size(); i++) {
                        if (positionMap.get(songList.get(i).sort) == null)
                            positionMap.put(songList.get(i).sort, i);
                    }
                }
                adapter.updateDataSet(songList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                if (isAZSort) {
                    recyclerView.addOnScrollListener(scrollListener);
                } else {
                    sideBar.setVisibility(View.INVISIBLE);
                    recyclerView.removeOnScrollListener(scrollListener);
                }
                LogUtils.showLog("load t");
                if (isFirstLoad) {
                    LogUtils.showLog("load");
                    frameLayout.removeAllViews();
                    //framelayout 创建了新的实例
                    ViewGroup p = (ViewGroup) view.getParent();
                    if (p != null) {
                        p.removeAllViewsInLayout();
                    }
                    frameLayout.addView(view);
                    isFirstLoad = false;
                }
            }
        }.execute();
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                sideBar.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.song_sort_by, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort_by_az://按名称排序
                mPreferences.setSongSortOrder(SortOrder.SongSortOrder.SONG_A_Z);
                reloadAdapter();
                return true;
            case R.id.menu_sort_by_date://按添加时间排序
                mPreferences.setSongSortOrder(SortOrder.SongSortOrder.SONG_DATE);
                reloadAdapter();
                return true;
            case R.id.menu_sort_by_artist://按歌手排序
                mPreferences.setSongSortOrder(SortOrder.SongSortOrder.SONG_ARTIST);
                reloadAdapter();
                return true;
            case R.id.menu_sort_by_album://按专辑排序
                mPreferences.setSongSortOrder(SortOrder.SongSortOrder.SONG_ALBUM);
                reloadAdapter();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** 异步加载recyclerview界面 */
    private class loadSongs extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if (mContext != null) {
                musicInfos = (ArrayList<MusicInfo>) MusicUtils.queryMusic(mContext, IConstants.START_FROM_LOCAL);

                for (int i = 0; i < musicInfos.size(); i++) {
                    char c = Pinyin.toPinyin(musicInfos.get(i).musicName.charAt(0)).charAt(0);
                }
                if (musicInfos != null)
                    adapter = new TabMusicAdapter(musicInfos);
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            recyclerView.setAdapter(adapter);
            if (mContext != null)
                recyclerView.addItemDecoration(new DividerItemDecoration(mContext,
                        DividerItemDecoration.VERTICAL_LIST));

        }

        @Override
        protected void onPreExecute() {

        }
    }

    public class TabMusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<MusicInfo> list;
        final static int FIRST_ITEM = 0;
        final static int ITEM = 1;

        public TabMusicAdapter (ArrayList<MusicInfo> mlist) {
            this.list = mlist;
        }

        /** 更新要显示的数据 */
        public void updateDataSet (ArrayList<MusicInfo> mlist) {
            this.list = mlist;
        }

        /** 判断布局类型 */
        @Override
        public int getItemViewType(int position) {
            return position == FIRST_ITEM ? FIRST_ITEM : ITEM;
        }

        /** 加载布局 */
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            RecyclerView.ViewHolder holder;
            if (viewType == FIRST_ITEM)
                holder = new CommonItemViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.common_item, viewGroup, false));
            else
                holder = new ListItemViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.fragment_music_common_item, viewGroup, false));
            return holder;
        }

        /** 加载数据 */
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        /** 播放全部列表项的ViewHolder */
        public class CommonItemViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
            /** 播放全部 */
            TextView textView;
            /** 选项菜单 */
            ImageView select;

            public CommonItemViewHolder(View itemView) {
                super(itemView);
                this.textView = (TextView) view.findViewById(R.id.play_all_number);
                this.select = (ImageView) view.findViewById(R.id.select);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                //TODO  播放全部？？
                LogUtils.toast(mContext, "播放全部" + getAdapterPosition());
                /*if(playMusic != null)
                    handler.removeCallbacks(playMusic);
                if(getAdapterPosition() > -1){
                    playMusic = new PlayMusic(0);
                    handler.postDelayed(playMusic,70);
                }*/
            }
        }

        /** 歌曲列表项的ViewHolder */
        public class ListItemViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
            /** 选项菜单 */
            ImageView moreOverflow;
            /** 主副标题 */
            TextView mainTitle, title;
            /** 是否正在播放的状态 */
            ImageView playState;

            public ListItemViewHolder(View itemView) {
                super(itemView);
                this.mainTitle = (TextView) view.findViewById(R.id.viewpager_list_toptext);
                this.title = (TextView) view.findViewById(R.id.viewpager_list_bottom_text);
                this.playState = (ImageView) view.findViewById(R.id.play_state);
                this.moreOverflow = (ImageView) view.findViewById(R.id.viewpager_list_button);
                moreOverflow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 选项菜单
                        LogUtils.toast(mContext, "position : " );
                        /*MoreFragment morefragment = MoreFragment.newInstance(
                                mList.get(getAdapterPosition() - 1), IConstants.MUSICOVERFLOW);
                        morefragment.show(getFragmentManager(), "music");*/
                    }
                });
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                //TODO
                LogUtils.toast(mContext, "************" );
                /*if(playMusic != null)
                    handler.removeCallbacks(playMusic);
                if(getAdapterPosition() > -1){
                    playMusic = new PlayMusic(getAdapterPosition() - 1);
                    handler.postDelayed(playMusic,70);
                }*/
            }
        }

        /*class PlayMusic implements Runnable{
            int position;
            public PlayMusic(int position){
                this.position = position;
            }

            @Override
            public void run() {
                long[] list = new long[mList.size()];
                HashMap<Long, MusicInfo> infos = new HashMap();
                for (int i = 0; i < mList.size(); i++) {
                    MusicInfo info = mList.get(i);
                    list[i] = info.songId;
                    info.islocal = true;
                    info.albumData = MusicUtils.getAlbumArtUri(info.albumId) + "";
                    infos.put(list[i], mList.get(i));
                }
                if (position > -1)
                    MusicPlayer.playAll(infos, list, position, false);
            }
        }*/
    }
}
