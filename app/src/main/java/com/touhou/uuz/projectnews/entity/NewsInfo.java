package com.touhou.uuz.projectnews.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by UUZ on 2017/2/20.
 */

public class NewsInfo implements Parcelable {
    public String title;//新闻标题
    public String date;//日期
    public String category;//新闻类别
    public String author_name;//作者
    public String newsContentUrl;//新闻内容的网址
    public String picUrl;//新闻图片的网址


    public static final Creator<NewsInfo> CREATOR = new Creator<NewsInfo>() {
        @Override
        public NewsInfo createFromParcel(Parcel in)
        {
       NewsInfo info =  new NewsInfo();
            info.title = in.readString();
            info.date = in.readString();
            info.category = in.readString();
            info.author_name = in.readString();
            info.newsContentUrl = in.readString();
            info.picUrl = in.readString();
            return info;
        }

        @Override
        public NewsInfo[] newArray(int size)
        {
            return new NewsInfo[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(category);
        dest.writeString(author_name);
        dest.writeString(newsContentUrl);
        dest.writeString(picUrl);
    }

    //parcelable

}
