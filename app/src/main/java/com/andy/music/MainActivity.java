package com.andy.music;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.andy.adapter.LvMenuItemAdapter;
import com.andy.item.LvMenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private ImageView barnet, barmusic, barfriends, search;
    private ArrayList<ImageView> tabs = new ArrayList<>();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏ActionBar区域
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏
        setContentView(R.layout.activity_main);

//        initActionBar();

        drawerLayout = (DrawerLayout) findViewById(R.id.fd);
        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);
        setUpDrawer();

    }

    private void initActionBar(){
        actionBar = this.getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);//TODO 两层效果？？？
        // 去掉默认标题栏
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        // Set up tabs
//        addTab(TabState.GROUPS, R.drawable.ic_tab_groups);
//        addTab(TabState.ALL, R.drawable.ic_tab_all);
//        addTab(TabState.FAVORITES, R.drawable.ic_tab_starred);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 为了让 Toolbar 的 Menu 有作用，这里不可以拿掉
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.bar_disco:
                msg += "Click bar_disco";
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.bar_music:
                msg += "Click bar_music";
                break;
            case R.id.bar_friends:
                msg += "Click bar_friends";
                break;
            case R.id.bar_search:
                msg += "Click bar_search";
                break;
        }
        if(!msg.equals("")) {
            toast(getApplicationContext(), msg);
        }
        return super.onOptionsItemSelected(item);
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


//    private ActionMenuView.OnMenuItemClickListener onMenuItemClick = new ActionBar.OnMenuItemClickListener() {
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//
//            return true;
//        }
//
//    };

    /** 侧滑菜单 */
    private void setUpDrawer() {
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
}
