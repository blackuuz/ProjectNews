package com.touhou.uuz.projectnews.config;

import com.touhou.uuz.projectnews.entity.NewsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UUZ on 2017/2/20.
 */

public class NewsConfig {
    public static String getNewsUrl(String newsType)
    {
     StringBuffer sb = new StringBuffer();
        sb.append("http://v.juhe.cn/toutiao/index?type=");
        sb.append(newsType);
        sb.append("&key=dd9b4c0144d39b0ae941f758c31ef27b");
        return sb.toString();

    }
    //准备一个集合用于存放用户收藏的集合
    /**
     * 准备一个集合用于存放用户收藏的集合
     * */
    public static List<NewsInfo> newsInfos = new ArrayList<>();
}
