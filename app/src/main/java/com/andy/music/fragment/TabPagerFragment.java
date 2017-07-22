package com.andy.music.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andy.music.R;
import com.andy.music.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地列表fragment.
 * 用于加载单曲/歌手/专辑/文件夹这4个fragment
 * Created by Andy on 2017/7/6.
 */

public class TabPagerFragment extends DialogFragment {
    private ViewPager viewPager;
    private int page = 0;
//    private ActionBar actionBar;//已经在TabActivity中设置
    private String[] title;

    public static TabPagerFragment newInstance(int page, String[] title) {
        TabPagerFragment fragment = new TabPagerFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("page_number", page);
        bundle.putStringArray("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setCurrentItem(page);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (title[0].equals("单曲")) //已经在TabActivity中设置
            actionBar.setTitle("本地音乐");*/
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mPreferences = PreferencesUtility.getInstance(mContext);
        if (getArguments() != null) {
            page = getArguments().getInt("page_number");
            title = getArguments().getStringArray("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
            viewPager.setOffscreenPageLimit(3);
        }

        final TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(R.color.text_color_black,R.color.theme_color_red);
                /*ThemeUtils.getThemeColorStateList(context,
                        R.color.theme_color_red).getDefaultColor());*/

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.theme_color_red));
                /*ThemeUtils.getThemeColorStateList(context,
                        R.color.theme_color_red).getDefaultColor());*/

        /*//已经在TabActivity中设置
        actionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.actionbar_back);
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        //以下弃用.改用在ActionBar中添加一个搜索.//已经在TabActivity中设置
        /*ImageView search = (ImageView) rootView.findViewById(R.id.bar_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                LogUtils.toast(getContext(), "LocalSearchActivity");
                *//*final Intent intent = new Intent(mContext, LocalSearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                mContext.startActivity(intent);*//*
            }
        });*/

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getChildFragmentManager());
        adapter.addFragment(new TabMusicFragment(), title[0]);
        adapter.addFragment(new TabArtistFragment(),title[1]);
        adapter.addFragment(new TabAlbumFragment(), title[2]);
        adapter.addFragment(new TabFolderFragment(),title[3]);

        viewPager.setAdapter(adapter);
    }

    static class TabAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            if(mFragments.size() > position)
                return mFragments.get(position);
            return null;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            // don't super !
        }
    }
}
