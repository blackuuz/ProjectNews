package com.touhou.uuz.projectnews.util;

import android.util.Log;

/**
 * Created by UUZ on 2017/2/13.
 */

public class LogUtil {
    private static boolean debag = true;
    public static void d(Object obj, String msg){
        if (debag) {
            Log.d(obj.getClass().getSimpleName(), msg);
        }
    }
}
