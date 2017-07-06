package com.andy.music.item;

/**
 * 主页面分类列表类
 * Created by Andy on 2017/7/5.
 */

public class MainFragmentItem {
    /** 信息标题 */
    public String title;
    /** 图片ID */
    public int avatar;
    /** 该类型的数量 */
    public int count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
