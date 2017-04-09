package com.touhou.uuz.projectnews.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/23 0023.
 */

public class CommonUtils {
    //准备格式化小数的类，并给定一个格式化规则,#.00表示保留小数点后2位
    private static DecimalFormat format=new DecimalFormat("#.00");
    public static String getFileSizeFormated(long filesize){
        if(filesize<1024){
            return  filesize+"B";
        }else if(filesize<1024*1024){
            return format.format((double)filesize/1024)+"KB";
        }else if(filesize<1024*1024*1024){
            return format.format((double)filesize/1024/1024)+"MB";
        }else{
            return format.format((double)filesize/1024/1024/1024)+"GB";
        }

    }
    public static String formatTime(long time){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }
}
