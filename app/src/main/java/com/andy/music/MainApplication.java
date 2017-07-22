package com.andy.music;

import android.app.Application;

/**
 * Created by Andy on 2017/7/6.
 */

public class MainApplication extends Application {

    public static Object context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
