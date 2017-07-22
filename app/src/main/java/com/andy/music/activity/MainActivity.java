package com.andy.music.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.andy.music.utils.LogUtils;
import com.andy.music.widgit.CustomViewPager;
import com.andy.music.adapter.CustomViewPagerAdapter;
import com.andy.music.adapter.LvMenuItemAdapter;
import com.andy.music.fragment.FriendsFragment;
import com.andy.music.fragment.NetworkFragment;
import com.andy.music.item.LvMenuItem;
import com.andy.music.R;
import com.andy.music.fragment.MainFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 主列表Activity.
 * 加载TabNetPagerFragment/MainFragment/FriendsFragment
 * Created by Andy on 2017/7/6.
 */

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏ActionBar区域的标题
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initStatusbar();//沉浸式状态栏
        initToolbar();  //导航栏
        setUpDrawer();  //侧滑菜单
        initAdapter();  //导航栏滑动效果
    }

    /** 设置透明导航栏和状态栏，即沉浸式状态栏.否则左侧滑无法全屏 */
    private void initStatusbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明底部菜单栏。因为底部设置一个可控的音乐播放控件，因此此处注释
            /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/

            /*//setTranslucentStatus(true);透明状态栏(弃用方法)
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.theme_color_red);
            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();*/
        }
    }

    /*Toolbar*/
    private Toolbar toolBar;
    private ImageView bar_menu, bar_discover, bar_music, bar_friends, bar_search;
    private ArrayList<ImageView> tabs = new ArrayList<>();

    /** 初始化导航栏 */
    private void initToolbar(){
        drawerLayout = (DrawerLayout) findViewById(R.id.fd);
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        toolBar.setTitle("");
        toolBar.setPadding(0,getStatusBarHeight(),0,0);//以系统状态栏的高度作为上边界
        bar_menu = (ImageView) findViewById(R.id.bar_menu);
        bar_discover = (ImageView) findViewById(R.id.bar_discover);
        bar_music = (ImageView) findViewById(R.id.bar_music);
        bar_friends = (ImageView) findViewById(R.id.bar_friends);
        bar_search = (ImageView) findViewById(R.id.bar_search);
        bar_menu.setOnClickListener(listener);
        bar_discover.setOnClickListener(listener);
        bar_music.setOnClickListener(listener);
        bar_friends.setOnClickListener(listener);
        bar_search.setOnClickListener(listener);
    }

    /** 获取系统状态栏的高度 */
    public int getStatusBarHeight() {
        int result = 50;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msg = "";
            switch (v.getId()) {
                case R.id.bar_menu:
                    drawerLayout.openDrawer(Gravity.LEFT);
                    break;
                case R.id.bar_discover:
                    customViewPager.setCurrentItem(0);
                    break;
                case R.id.bar_music:
                    customViewPager.setCurrentItem(1);
                    break;
                case R.id.bar_friends:
                    customViewPager.setCurrentItem(2);
                    break;
                case R.id.bar_search:
                    msg += "Click bar_search";
                    LogUtils.toast(getApplicationContext(), msg);
                    /*final Intent intent = new Intent(MainActivity.this, NetSearchWordsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    MainActivity.this.startActivity(intent);*/
                    break;
            }
        }
    };

    /** 侧滑菜单布局 */
    private DrawerLayout drawerLayout;
    /** 侧滑菜单视图 */
    private ListView mLvLeftMenu;
    /** 侧滑菜单选项内容 */
    private List<LvMenuItem> mItems = new ArrayList<LvMenuItem>(
            Arrays.asList(
                    new LvMenuItem(R.mipmap.topmenu_icn_night, "夜间模式"),
                    new LvMenuItem(R.mipmap.topmenu_icn_skin, "主题换肤"),
                    new LvMenuItem(R.mipmap.topmenu_icn_time, "定时关闭音乐"),
                    new LvMenuItem(R.mipmap.topmenu_icn_vip, "下载歌曲品质"),
                    new LvMenuItem(R.mipmap.topmenu_icn_exit, "退出")
            ));

    /** 侧滑菜单 */
    private void setUpDrawer() {
        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);
        LayoutInflater inflater = LayoutInflater.from(this);
        mLvLeftMenu.addHeaderView(inflater.inflate(R.layout.widgit_lefemenu_header, mLvLeftMenu, false));
        mLvLeftMenu.setAdapter(new LvMenuItemAdapter(this, mItems));//侧滑菜单适配，传content和list
        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        /*drawerLayout.closeDrawers();*/
                        break;
                    case 2:
                        /*CardPickerDialog dialog = new CardPickerDialog();
                        dialog.setClickListener(MainActivity.this);
                        dialog.show(getSupportFragmentManager(), "theme");
                        drawerLayout.closeDrawers();*/

                        break;
                    case 3:
                        /*TimingFragment fragment3 = new TimingFragment();
                        fragment3.show(getSupportFragmentManager(), "timing");
                        drawerLayout.closeDrawers();*/

                        break;
                    case 4:
                        /*BitSetFragment bfragment = new BitSetFragment();
                        bfragment.show(getSupportFragmentManager(), "bitset");
                        drawerLayout.closeDrawers();*/

                        break;
                    case 5:
                        /*if (MusicPlayer.isPlaying()) {
                            MusicPlayer.playOrPause();
                        }
                        unbindService();
                        finish();
                        drawerLayout.closeDrawers();*/
                        break;
                    default:
                        LogUtils.toast(getApplicationContext(),"主题");
                        return;
//                        break;
                }
                LogUtils.toast(getApplicationContext(),""+mItems.get(position-1).name);
            }
        });
    }

    private CustomViewPager customViewPager;
    /** 初始化滑动导航栏 */
    private void initAdapter() {
        tabs.add(bar_discover);
        tabs.add(bar_music);
        tabs.add(bar_friends);
        customViewPager = (CustomViewPager) findViewById(R.id.main_viewpager);
        final MainFragment mainFragment = new MainFragment();
        final NetworkFragment networkFragment = new NetworkFragment();
        final FriendsFragment friendsFragment = new FriendsFragment();
        CustomViewPagerAdapter customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        customViewPagerAdapter.addFragment(networkFragment);
        customViewPagerAdapter.addFragment(mainFragment);
        customViewPagerAdapter.addFragment(friendsFragment);
        customViewPager.setAdapter(customViewPagerAdapter);
        customViewPager.setCurrentItem(1);
        bar_music.setSelected(true);
        customViewPager.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /** 更新滑动导航控件的选中状态 */
    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            if (position == i) {
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }

    private long time = 0;
    /**
     * 双击返回桌面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 1500)) {
                LogUtils.toast(this, "连按两次返回桌面");
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
