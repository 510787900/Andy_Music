package com.andy.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.andy.music.R;
import com.andy.music.fragment.TabPagerFragment;
import com.andy.music.utils.LogUtils;

import java.lang.reflect.Field;

/**
 * 本地列表Activity.
 * 加载TabPagerFragment/TabArtistFragment/TabAlbumFragment/TabFolderFragment 4个gragment
 * Created by Andy on 2017/7/6.
 */

public class TabActivity extends BaseActivity{
    private int page, artistId, albumId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            page = getIntent().getIntExtra("page_number", 0);
            artistId = getIntent().getIntExtra("artist", 0);
            albumId = getIntent().getIntExtra("album", 0);
        }
        setContentView(R.layout.activity_tab);

        initActionBar();

        if (artistId != 0) {
            /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ArtistDetailFragment fragment = ArtistDetailFragment.newInstance(artistId);
            transaction.hide(getSupportFragmentManager().findFragmentById(R.id.tab_container));
            transaction.add(R.id.tab_container, fragment);
            transaction.addToBackStack(null).commit();*/
        }
        if (albumId != 0) {

        }

        String[] title = {"单曲", "歌手", "专辑", "文件夹"};
        TabPagerFragment fragment = TabPagerFragment.newInstance(page, title);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.tab_container, fragment);
        transaction.commitAllowingStateLoss();
    }

    private ActionBar actionBar;
    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setTitle("本地音乐");
        actionBar.setDisplayHomeAsUpEnabled(true);//左上角返回键
        setOverflowShowingAlways();//表示不管有没有虚拟按键都显示右上角的更多选项菜单
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home://回到父Activity.
                LogUtils.toast(this, "actionbar_home");
                /*其中，调用NavUtils.getParentActivityIntent()方法
                可以获取到跳转至父Activity的Intent。
                然后如果父Activity和当前Activity是在同一个Task中的，
                则直接调用navigateUpTo()方法进行跳转。
                如果不是在同一个Task中的，
                则需要借助TaskStackBuilder来创建一个新的Task。*/
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
            case R.id.actionbar_search:
                LogUtils.toast(this, "actionbar_search");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** 表示不管有没有虚拟按键都显示右上角的更多选项菜单 */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
