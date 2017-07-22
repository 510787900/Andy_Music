package com.andy.music.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TextViewCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.andy.music.item.LvMenuItem;
import com.andy.music.R;

import java.util.List;

/** 侧滑菜单的适配器 */
public class LvMenuItemAdapter extends BaseAdapter {
    /** 选项的图标尺寸 */
    private final int mIconSize;
    /** 布局加载器 */
    private LayoutInflater mInflater;
    private Context mContext;
    /** 侧滑选项内容 */
    private List<LvMenuItem> mItems;

    public LvMenuItemAdapter(Context context, List<LvMenuItem> items) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mItems = items;
        mIconSize = context.getResources().getDimensionPixelSize(R.dimen.drawer_icon_size);//32dp
    }

    @Override
    public int getCount() {
        return mItems.size();
    }


    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LvMenuItem item = mItems.get(position);
        switch (item.type) {
            case LvMenuItem.TYPE_NORMAL:
                if (convertView == null) {
                    convertView = mInflater.inflate(
                            R.layout.design_drawer_item, parent, false);
                }
                TextView itemView = (TextView) convertView;
                itemView.setText(item.name);
                itemView.setTextColor(Color.BLACK);
                Drawable icon = mContext.getResources().getDrawable(item.icon);
                // setIconColor(icon);
                if (icon != null) {
                    icon.setBounds(0, 0, mIconSize, mIconSize);
                    TextViewCompat.setCompoundDrawablesRelative(
                            itemView, icon, null, null, null);
                }
                break;
            case LvMenuItem.TYPE_NO_ICON:
                if (convertView == null) {
                    convertView = mInflater.inflate(
                            R.layout.design_drawer_item_subheader, parent, false);
                }
                TextView subHeader = (TextView) convertView;
                subHeader.setText(item.name);
                break;
            case LvMenuItem.TYPE_SEPARATOR:
                if (convertView == null) {
                    convertView = mInflater.inflate(
                            R.layout.design_drawer_item_separator, parent, false);
                }
                break;
        }

        return convertView;
    }

    public void setIconColor(Drawable icon) {
        int textColorSecondary = android.R.attr.textColorSecondary;
        TypedValue value = new TypedValue();
        if (!mContext.getTheme().resolveAttribute(textColorSecondary, value, true)) {
            return;
        }
        int baseColor = mContext.getResources().getColor(value.resourceId);
        icon.setColorFilter(baseColor, PorterDuff.Mode.MULTIPLY);
    }
}