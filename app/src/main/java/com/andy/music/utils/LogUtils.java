package com.andy.music.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 打印日志帮助类
 * Created by Andy on 2017/7/5.
 */

public class LogUtils {
    public static boolean showLog = true;
    private static Toast toast = null;
    public static void toast(Context context, String msg) {
        toast(context, msg, Toast.LENGTH_SHORT);
    }

    public static void toast(Context context, String msg, int len) {
        if (toast != null) {
//            toast.cancel();     //cancel 之后只显示一次？
            toast.setText(msg);
            toast.setDuration(len);
        } else {
            toast = Toast.makeText(context, msg, len);
        }
        toast.show();
    }

    public static void showLog (String msg) {
        showLog("e", "huyonghua", msg);
    }

    public static void showLog (String type, String Tag , String msg) {
        if (!showLog){
            return;
        }
        switch (type) {
            case "v":
                Log.v(Tag, msg);
                break;
            case "d":
                Log.d(Tag, msg);
                break;
            case "i":
                Log.i(Tag, msg);
                break;
            case "w":
                Log.w(Tag, msg);
                break;
            case "e":
                Log.e(Tag, msg);
                break;
            default:
                Log.e(Tag, msg);
                break;
        }
    }
}
