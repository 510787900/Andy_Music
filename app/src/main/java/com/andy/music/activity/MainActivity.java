package com.andy.music.activity;

import android.content.Context;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.andy.music.adapter.CustomViewPager;
import com.andy.music.adapter.CustomViewPagerAdapter;
import com.andy.music.adapter.LvMenuItemAdapter;
import com.andy.music.fragment.FriendsFragment;
import com.andy.music.fragment.TabNetPagerFragment;
import com.andy.music.item.LvMenuItem;
import com.andy.music.R;
import com.andy.music.fragment.MainFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏ActionBar区域
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initState();//沉浸式状态栏
        initToolbar();//导航栏
        setUpDrawer();//侧滑菜单
        initAdapter();//导航栏滑动效果
    }

    /** 设置透明导航栏和状态栏，即沉浸式状态栏 */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /*Toolbar*/
    private Toolbar toolBar;
    private ImageView bar_menu, bar_discover, bar_music, bar_friends, bar_search;
    private ArrayList<ImageView> tabs = new ArrayList<>();

    private void initToolbar(){
        drawerLayout = (DrawerLayout) findViewById(R.id.fd);
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        toolBar.setTitle("");
        toolBar.setPadding(0,getStatusBarHeight(),0,0);
//        toolBar.setTitleTextColor(Color.WHITE);
//        titleName = getView(R.id.title_name);
//        setSupportActionBar(toolBar);
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
                    msg += "Click bar_discover";
                    toast(getApplicationContext(), msg);
                    customViewPager.setCurrentItem(0);
                    break;
                case R.id.bar_music:
                    msg += "Click bar_music";
                    toast(getApplicationContext(), msg);
                    customViewPager.setCurrentItem(1);
                    break;
                case R.id.bar_friends:
                    msg += "Click bar_friends";
                    toast(getApplicationContext(), msg);
                    customViewPager.setCurrentItem(2);
                    break;
                case R.id.bar_search:
                    msg += "Click bar_search";
                    toast(getApplicationContext(), msg);
//                    final Intent intent = new Intent(MainActivity.this, NetSearchWordsActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    MainActivity.this.startActivity(intent);
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
        mLvLeftMenu.addHeaderView(inflater.inflate(R.layout.nav_header_main, mLvLeftMenu, false));
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
                        toast(getApplicationContext(),"主题");
                        return;
//                        break;
                }
                toast(getApplicationContext(),""+mItems.get(position-1).name);
            }
        });
    }

    private CustomViewPager customViewPager;
    private void initAdapter() {
        tabs.add(bar_discover);
        tabs.add(bar_music);
        tabs.add(bar_friends);
        customViewPager = (CustomViewPager) findViewById(R.id.main_viewpager);
        final MainFragment mainFragment = new MainFragment();
        final TabNetPagerFragment tabNetPagerFragment = new TabNetPagerFragment();
        final FriendsFragment friendsFragment = new FriendsFragment();
        CustomViewPagerAdapter customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        customViewPagerAdapter.addFragment(tabNetPagerFragment);
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

    /** 更新控件状态 */
    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            if (position == i) {
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }

    private Toast toast = null;
    private void toast(Context context, String msg) {
        toast(context, msg, Toast.LENGTH_SHORT);
    }

    private void toast(Context context, String msg, int len) {
        if (toast != null) {
//            toast.cancel();     //cancel 之后只显示一次？
            toast.setText(msg);
            toast.setDuration(len);
        } else {
            toast = Toast.makeText(context, msg, len);
        }
        toast.show();
    }
}
