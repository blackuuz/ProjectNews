package com.touhou.uuz.projectnews;

import android.app.Application;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by UUZ on 2017/4/9.
 */

public class MyApp extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        ShareSDK.initSDK(this);
    }
}
