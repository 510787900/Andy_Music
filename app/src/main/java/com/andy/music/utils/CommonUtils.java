package com.andy.music.utils;

import android.os.Build;

/**
 *
 * Created by Andy on 2017/7/5.
 */

public class CommonUtils {


    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
