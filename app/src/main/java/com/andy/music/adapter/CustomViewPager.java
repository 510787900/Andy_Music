package com.andy.music.adapter;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义滑动适配器
 * Created by Andy on 2017/7/3.
 */

public class CustomViewPager extends ViewPager{
    /** 屏幕坐标 */
    PointF downPoint = new PointF();

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录按下时候的坐标
                downPoint.x = ev.getX();
                downPoint.y = ev.getY();
                if (this.getChildCount() > 1) { //有内容，多于1个时
                    // 通知其父控件，现在进行的是本控件的操作，不允许拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.getChildCount() > 1) { //有内容，多于1个时
                    // 通知其父控件，现在进行的是本控件的操作，不允许拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 在up时判断是否按下和松手的坐标为一个点
                if (PointF.length(ev.getX() - downPoint.x, ev.getY()
                        - downPoint.y) < (float) 5.0) {
                    onSingleTouch(this);
                    return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    OnSingleTouchListener onSingleTouchListener;

    public void onSingleTouch(View v) {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch(v);
        }
    }

    public interface OnSingleTouchListener {
        public void onSingleTouch(View v);
    }

    public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }
}
