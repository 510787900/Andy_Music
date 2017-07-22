package com.andy.music.widgit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.andy.music.R;
import com.andy.music.utils.LogUtils;

/**
 * 歌曲列表的分类项(显示A.B.C...)
 * Created by Andy on 2017/7/22.
 */

public class SideBar extends View{

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.search_indexbar_bg_prs);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /** 26个字母 + # : 用来分类 */
    public static String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private int choose = -1;// 选中的分类
    /** 画笔 */
    private Paint paint = new Paint();
    /** 显示字母 */
    private TextView mTextDialog;

    public void setView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public void setSelected(String nowChar) {
        LogUtils.showLog("setSelected:" + nowChar);
        if (nowChar != null) {
            for (int i = 0; i < letter.length; i++) {
                if (letter[i].equals(nowChar)) {
                    choose = i;
                    break;
                }
                if (i == letter.length - 1) {
                    choose = -1;
                }
            }
            invalidate();//刷新整个view
        }
    }

    /**
     * 重写绘制方法
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.removeCallbacks(runnable);
        //获取焦点时改变背景颜色.
        int height = getHeight();                   //获取高度
        int width = getWidth();                     //获取宽度
        int singleHeight = height / letter.length;  //获取每个字母的高度

        for (int i = 0; i < letter.length; i++) {
            paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.SANS_SERIF);
            paint.setAntiAlias(true);
            paint.setTextSize(30);

            //选中的状态
            if (choose == i) {
                paint.setColor(Color.WHITE);
                paint.setFakeBoldText(true);
            }

            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(letter[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(letter[i], xPos, yPos, paint);
            paint.reset();// 重置画笔
        }
        this.postDelayed(runnable, 2000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            SideBar.this.setVisibility(VISIBLE);
        }
    };

    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int num = (int) (y / getHeight() * letter.length);
        // 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            case MotionEvent.ACTION_UP:

                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                //  this.postDelayed(runnable,2000);
                break;
            case MotionEvent.ACTION_DOWN:
                this.removeCallbacks(runnable);
                SideBar.this.setVisibility(VISIBLE);
                break;

            default:
                //setBackgroundResource(R.drawable.sort_listview_sidebar_background);
                if (oldChoose != num) {
                    if (num >= 0 && num < letter.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(letter[num]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(letter[num]);
                            mTextDialog.setVisibility(View.VISIBLE);
                            //这里是指的在屏幕中央显示当前点击的一个A，B，C，D...的一个状态显示。
                        }

                        choose = num;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }
}
