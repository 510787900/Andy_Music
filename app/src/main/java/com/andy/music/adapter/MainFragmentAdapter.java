package com.andy.music.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.music.R;
import com.andy.music.activity.TabActivity;
import com.andy.music.item.MainFragmentItem;
import com.andy.music.info.Playlist;
import com.andy.music.utils.LogUtils;

import java.util.ArrayList;

/**
 * 本地界面的列表适配器
 * Created by Andy on 2017/7/4.
 */

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ItemHolder>{
    /** 创建的歌单 */
    private ArrayList<Playlist> playlists = new ArrayList<>();
    /** 收藏的歌单 */
    private ArrayList<Playlist> netplaylists = new ArrayList<>();
    /** 是否展开创建的歌单 */
    private boolean createdExpanded = true;
    /** 是否展开收藏的歌单 */
    private boolean collectExpanded = true;
    private Context mContext;
    /** 展开的列表 */
    private ArrayList itemResults = new ArrayList();
    private boolean isLoveList = true;

    public MainFragmentAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * 刷新列表信息
     * @param itemResults
     * @param playlists     创建的歌单列表
     * @param netplaylists  收藏的歌单列表
     */
    public void updateResults(ArrayList itemResults, ArrayList<Playlist> playlists,
                              ArrayList<Playlist> netplaylists) {
        isLoveList = true;
        this.itemResults = itemResults;
        this.playlists = playlists;
        this.netplaylists = netplaylists;
    }

    /**
     * 刷新列表信息
     * @param playlists     创建的歌单列表
     */
    public void updatePlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //加载布局
        switch (viewType) {
            case 0://本地音乐等(4个)
                View view0 = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.fragment_main_item, viewGroup, false);
                ItemHolder itemHolder0 = new ItemHolder(view0);
                return itemHolder0;

            case 1://喜欢的音乐
                /*if(isLoveList){
                    View view1 = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.fragment_main_playlist_item_love, viewGroup, false);
                    ItemHolder itemHolder1 = new ItemHolder(view1);
                    return itemHolder1;
                }//其他创建的分组*/
                View view1 = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.fragment_main_playlist_item, viewGroup, false);
                ItemHolder itemHolder1 = new ItemHolder(view1);
                return itemHolder1;

            case 2://创建的歌单
                View view2 = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.fragment_main_item_expandable, viewGroup, false);
                ItemHolder itemHolder2 = new ItemHolder(view2);
                return itemHolder2;

            case 3://收藏的歌单
                View view3 = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.fragment_main_item_expandable, viewGroup, false);
                ItemHolder itemHolder3 = new ItemHolder(view3);
                return itemHolder3;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int i) {
        //加载内容
        switch (getItemViewType(i)){
            case 0://本地音乐等(4个)
                MainFragmentItem item = (MainFragmentItem) itemResults.get(i);
                holder.text_local_title.setText(item.title);
                holder.text_local_count.setText("(" + item.count + ")");
                holder.img_local.setImageResource(item.avatar);
                setOnListener(holder, i);
                break;
            case 1://喜欢的音乐等分组
                Playlist playlist = (Playlist) itemResults.get(i);
                if (createdExpanded && playlist.author.equals("local")) {
                    if (playlist.albumArt != null)
                        holder.img_love_albumArt.setImageURI(Uri.parse(playlist.albumArt));
                    holder.text_love_title.setText(playlist.name);
                    holder.text_love_songcount.setText(playlist.songCount + "首");
                }
                if (collectExpanded && !playlist.author.equals("local")) {
                    if (playlist.albumArt != null)
                        holder.img_love_albumArt.setImageURI(Uri.parse(playlist.albumArt));
                    holder.text_love_title.setText(playlist.name);
                    holder.text_love_songcount.setText(playlist.songCount + "首");
                }
                setOnPlaylistListener(holder, i, playlist.id, playlist.albumArt, playlist.name);
                break;
            case 2://创建的歌单
                holder.text_section.setText("创建的歌单(" + playlists.size() + ")");
                holder.img_section_menu.setImageResource(R.drawable.list_icn_more);
                setSectionListener(holder, i);
                break;
            case 3://收藏的歌单
                holder.text_section.setText("收藏的歌单(" + playlists.size() + ")");
                holder.img_section_menu.setImageResource(R.drawable.list_icn_more);
                setSectionListener(holder, i);
                break;
        }
    }

    /** 设置本地音乐等(4个)列表 */
    private void setOnListener(ItemHolder holder, final int i) {
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = null;
                        switch (i) {
                            case 0:
                                intent = new Intent(mContext, TabActivity.class);
                                intent.putExtra("page_number", 0);
                                mContext.startActivity(intent);
//                                LogUtils.toast(mContext, "TabActivity *** 0");
                                break;
                            case 1:
//                                intent = new Intent(mContext, RecentActivity.class);
                                LogUtils.toast(mContext, "RecentActivity");
                                break;
                            case 2:
//                                intent = new Intent(mContext, DownActivity.class);
                                LogUtils.toast(mContext, "DownActivity");
                                break;
                            case 3:
                                intent = new Intent(mContext, TabActivity.class);
                                intent.putExtra("page_number", 1);
                                mContext.startActivity(intent);
//                                LogUtils.toast(mContext, "TabActivity *** 1");
                                break;
                        }
//                        mContext.startActivity(intent);
                    }
                }, 60);
            }
        });
    }

    /** 设置喜欢的音乐等分组 */
    private void setOnPlaylistListener(ItemHolder holder, final int position, long playlistid,
                                       String albumArt, String playlistname) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*Intent intent = new Intent(mContext, PlaylistActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("islocal", true);
                        intent.putExtra("playlistid", playlistid + "");
                        intent.putExtra("albumart", albumArt);
                        intent.putExtra("playlistname", playlistname);
                        mContext.startActivity(intent);*/
                        LogUtils.toast(mContext, "PlaylistActivity");
                    }
                }, 60);
            }
        });

        holder.img_love_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (5 == position)
                            LogUtils.toast(mContext, "此歌单不可删除");
                        else {
                            deleteList();
                        }
                        return true;
                    }
                });
                popupMenu.inflate(R.menu.popmenu);
                popupMenu.show();
            }
        });
    }

    /** 删除歌单 */
    private void deleteList() {
        new AlertDialog.Builder(mContext)
                .setTitle(mContext.getString(R.string.sure_to_delete_music))
                .setPositiveButton(mContext.getString(R.string.sure),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*PlaylistInfo.getInstance(mContext).deletePlaylist(playlistid);
                        PlaylistsManager.getInstance(mContext).delete(playlistid);
                        Intent intent = new Intent();
                        intent.setAction(IConstants.PLAYLIST_COUNT_CHANGED);
                        mContext.sendBroadcast(intent);*/
                        LogUtils.toast(mContext, "执行删除歌单");
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(mContext.getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /** 设置创建、收藏的歌单 */
    private void setSectionListener(ItemHolder holder, int i) {
        holder.img_section_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(mContext, PlaylistManagerActivity.class);
                mContext.startActivity(intent);*/
                LogUtils.toast(mContext, "PlaylistManagerActivity");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (itemResults == null) {
            return 0;
        }
        if (!createdExpanded && playlists != null) {
            itemResults.removeAll(playlists);
        }
        if (!collectExpanded) {
            itemResults.removeAll(netplaylists);
        }
        return itemResults.size();
    }

    /** 重写获取视图类型的函数.自定义 */
    @Override
    public int getItemViewType(int position) {
//        LogUtils.showLog("itemResults.size() = " + itemResults.size());
//        LogUtils.showLog("position = " + position);
        if (getItemCount() == 0 || position >= itemResults.size()) {
            return -1;
        }
        if (itemResults.get(position) instanceof MainFragmentItem) {
            return 0;//如果是MainFragmentItem类型，说明是本地音乐等4个列表
        }
        if (itemResults.get(position) instanceof Playlist) {
            return 1;//如果是Playlist类型，说明是歌单列表
        }
        if (itemResults.get(position) instanceof String) {
            if (((String) itemResults.get(position)).equals(
                    mContext.getResources().getString(R.string.collect_playlists)))
                return 3;
        }
        return 2;
    }

    @Override
    public void onViewRecycled(ItemHolder holder) {
        //TODO 是否要注释掉super行
//        super.onViewRecycled(holder);
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected ImageView img_local;
        protected TextView text_local_title, text_local_count;
        protected ImageView img_love_albumArt, img_love_menu;
        protected TextView text_love_title, text_love_songcount;
        protected ImageView img_section, img_section_menu;
        protected TextView text_section;

        public ItemHolder(View itemView) {
            super(itemView);
            //本地音乐等(4个)
            this.img_local = (ImageView) itemView
                    .findViewById(R.id.fragment_main_item_img);
            this.text_local_title = (TextView) itemView
                    .findViewById(R.id.fragment_main_item_title);
            this.text_local_count = (TextView) itemView
                    .findViewById(R.id.fragment_main_item_count);
            //喜欢的音乐
            this.img_love_albumArt = (ImageView) itemView
                    .findViewById(R.id.fragment_main_playlist_item_img);
            this.text_love_title = (TextView) itemView
                    .findViewById(R.id.fragment_main_playlist_item_title);
            this.text_love_songcount = (TextView) itemView
                    .findViewById(R.id.fragment_main_playlist_item_count);
            this.img_love_menu = (ImageView) itemView
                    .findViewById(R.id.fragment_main_playlist_item_menu);
            //创建、收藏的歌单
            this.img_section = (ImageView) itemView
                    .findViewById(R.id.expand_img);
            this.img_section_menu = (ImageView) itemView
                    .findViewById(R.id.expand_menu);
            this.text_section = (TextView) itemView
                    .findViewById(R.id.expand_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ObjectAnimator animator = null;
            animator = ObjectAnimator.ofFloat(img_section, "rotation", 90, 0);//旋转90度展开
            animator.setDuration(100);
            animator.setRepeatCount(0);
            animator.setInterpolator(new LinearInterpolator());

            //展开和隐藏的设置
            switch (getItemViewType()){
                case 2:
                    if (createdExpanded) {
                        itemResults.removeAll(playlists);
                        updateResults(itemResults, playlists, netplaylists);
                        notifyItemRangeChanged(5, playlists.size());
                        animator.start();
                        createdExpanded = false;
                    } else {
                        /*if (collectExpanded) itemResults.removeAll(netplaylists);
                        itemResults.remove("收藏的歌单");
                        itemResults.addAll(playlists);
                        itemResults.add("收藏的歌单");
                        if (collectExpanded) itemResults.addAll(netplaylists);*/
                        itemResults.addAll(5, playlists);
                        updateResults(itemResults, playlists, netplaylists);
                        notifyItemRangeInserted(5, playlists.size());
                        animator.reverse();
                        createdExpanded = true;
                    }
                    break;
                case 3:
                    if (collectExpanded) {
                        itemResults.removeAll(netplaylists);
                        updateResults(itemResults, playlists, netplaylists);
                        notifyItemRangeChanged(itemResults.size(), playlists.size());
                        animator.start();
                        collectExpanded = false;
                    } else {
                        itemResults.addAll(itemResults.size(), netplaylists);
                        updateResults(itemResults, playlists, netplaylists);
                        notifyItemRangeInserted(itemResults.size(), netplaylists.size());
                        animator.reverse();
                        collectExpanded = true;
                    }
                    break;
            }
        }
    }
}
